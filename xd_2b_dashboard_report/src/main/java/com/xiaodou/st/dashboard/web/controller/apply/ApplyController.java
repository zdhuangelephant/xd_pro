package com.xiaodou.st.dashboard.web.controller.apply;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.student.StudentService;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("applyController")
@RequestMapping("/apply")
public class ApplyController extends BaseController {

  @Resource
  ApplyService applyService;
  @Resource
  StudentService studentService;
  @Resource
  ClassService classService;
  @Resource
  ProductService productService;

  /**
   * 报名列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param applyDO
   * @return
   */
  @RequestMapping("/apply_list")
  public ModelAndView applyList(ApplyDO applyDO, HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("/apply/applyList");
    HttpSession session = request.getSession();
    Page<ApplyDO> pg = new Page<ApplyDO>();
    Integer pageNo = applyDO.getPageNo();
    if (pageNo == null) {
      pageNo = 1;
      applyDO.setPageNo(pageNo);
    }
    pg.setPageNo(pageNo);
    Integer pageSize = applyDO.getPageSize();
    if (null == pageSize) pageSize = 10;
    pg.setPageSize(pageSize);
    mv.addObject("adminUser", super.getAdminUser());
    // List<ApplyDO> listApply = pg.getResult();
    mv.addObject("applyDO", applyDO);
    session.setAttribute("applyDO", applyDO);
    // select下拉框数据
    mv.addObject("listExamDate", productService.listExamDate());
    mv.addObject("listCategory", productService.listCategory());
    mv.addObject("listClass", classService.listClass());
    if (null != applyDO && null != applyDO.getCatId()) {
      mv.addObject("listProduct", productService.listProduct(applyDO.getCatId()));
    }
    mv.addObject("pageSize", pageSize);
    Page<ApplyDO> page = applyService.listApply(applyDO, pg);
    if (null == page || CollectionUtils.isEmpty(page.getResult())) {
      mv.addObject("totalPage", 0);
      mv.addObject("totalCount", 0);
      mv.addObject("pageNo", 1);
      return mv;
    }
    mv.addObject("listApply", page.getResult());
    mv.addObject("totalPage", page.getTotalPage());
    mv.addObject("totalCount", page.getTotalCount());
    mv.addObject("pageNo", page.getPageNo());
    return mv;
  }

  @RequestMapping("/preview_apply")
  public String previewApply(Long orderNumber) {
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderNumber(orderNumber);
    List<ApplyDO> listDO = applyService.listApply(applyDO);
    return FastJsonUtil.toJson(listDO);
  }

  /**
   * 
   * @description 报名
   * @author 李德洪
   * @Date 2017年4月5日
   * @param ado
   * @return
   */
  @RequestMapping("/save_apply")
  @ResponseBody
  public String saveApply(ApplyDO ado) {
    List<ApplyDO> hasApplyList = applyService.listApplyBySid(ado.getStudentId());
    if (null != hasApplyList && !hasApplyList.isEmpty()) {
      for (ApplyDO hasAdo : hasApplyList) {
        if (!ado.getCatId().equals(hasAdo.getCatId())){
          return "报名专业有误！同一学生在一个考期内只能报名一个专业下的课程。可在学生列表中点击“查看”，查询该学生的报课记录。";
        }
        if (ado.getProductId().equals(hasAdo.getProductId())){
          return "报名失败！已报名，请勿重复操作。";
        }
      }
    }
    AdminUser adminUser = super.getAdminUser();
    ado.setPilotUnitId(adminUser.getUnitId());
    ado.setPilotUnitName(adminUser.getUnitName());
    ado.setAdminId(adminUser.getUserId());
    ado.setAdminName(adminUser.getRealName());
    ado.setCreateTime(new Timestamp(System.currentTimeMillis()));
    StudentDO sdo = studentService.getStudent(ado.getStudentId());
    ado.setAdmissionCardCode(sdo.getAdmissionCardCode());
    ado.setClassId(sdo.getClassId());
    ado.setClassName(sdo.getClassName());
    ado.setStudentName(sdo.getRealName());
    ado.setTelephone(sdo.getTelephone());
    boolean flag = false;
    try {
      flag = applyService.saveApply(ado);
    } catch (Exception e) {
      if (e instanceof DuplicateKeyException) {
        return "报课失败。报名课程的课程代码重复。";
      }
      LoggerUtil.error("saveApply", e);
    }
    if(!flag){
      LoggerUtil.error("saveApply-报名失败", new Exception());
    }
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 刪除報名
   * @author 李德洪
   * @Date 2017年4月11日
   * @param ado
   * @return
   */
  @RequestMapping("/remove_apply")
  @ResponseBody
  public String removeApply(Long applyId) {
    return String.valueOf(applyService.removeApply(applyId));
  }


  @RequestMapping("/batch_save_apply")
  @ResponseBody
  public String batchSaveApply(ApplyDO ado, Integer productId, String studentIds) {
    Map<String, Integer> map = Maps.newHashMap();
    // 报名成功数
    Integer saveCount = 0;
    // 重复专业数
    Integer catRepetCount = 0;
    map.put("saveCount", saveCount);
    map.put("catRepetCount", catRepetCount);
    if (StringUtils.isJsonBlank(studentIds)) return FastJsonUtil.toJson(map);
    // 逆向逻辑,将已经报名的学生ID剔除,然后将剩余的学生进行报名操作
    List<String> allStudentIdList = Lists.newArrayList(studentIds.split(","));
    ApplyDO query = new ApplyDO();
    query.setProductId(productId);
    List<ApplyDO> hasApplyList = applyService.listApplyBySidList(allStudentIdList);
    if (null != hasApplyList && !hasApplyList.isEmpty()) {
      for (ApplyDO apply : hasApplyList)
        if (null != apply && null != apply.getStudentId()) {
          if (!ado.getCatId().equals(apply.getCatId())) {
            catRepetCount++;
          }
          if (!ado.getCatId().equals(apply.getCatId())
              || ado.getProductId().equals(apply.getProductId())) {
            allStudentIdList.remove(apply.getStudentId().toString());
          }
        }
      map.put("catRepetCount", catRepetCount);
    }
    AdminUser adminUser = super.getAdminUser();
    if (null == allStudentIdList || allStudentIdList.isEmpty()) return FastJsonUtil.toJson(map);
    List<StudentDO> sdoList = studentService.listStudent(allStudentIdList);
    if (null == sdoList || sdoList.isEmpty()) {
      return FastJsonUtil.toJson(map);
    }
    List<ApplyDO> applyDoList = Lists.newArrayList();
    for (StudentDO sdo : sdoList) {
      ApplyDO applyDO = new ApplyDO();
      applyDO.copyApply(ado);
      applyDO.setPilotUnitId(adminUser.getUnitId());
      applyDO.setPilotUnitName(adminUser.getUnitName());
      applyDO.setAdminId(adminUser.getUserId());
      applyDO.setAdminName(adminUser.getRealName());
      applyDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
      applyDO.setAdmissionCardCode(sdo.getAdmissionCardCode());
      applyDO.setClassId(sdo.getClassId());
      applyDO.setClassName(sdo.getClassName());
      applyDO.setStudentName(sdo.getRealName());
      applyDO.setTelephone(sdo.getTelephone());
      applyDO.setStudentId(sdo.getId());
      applyDoList.add(applyDO);
    }
    // 这里使用了取巧的批量插入方法,插入的字段值必须统一,xml中的字段是根据此方法定制的,使用时需要注意
    try {
      saveCount = applyService.saveApplyList(applyDoList);
      map.put("saveCount", saveCount);
    } catch (Exception e) {
      if (e instanceof DuplicateKeyException) {
        map.put("error", 0);
      }
      LoggerUtil.error("batchSaveApply", e);
    }
    return FastJsonUtil.toJson(map);
  }

  @RequestMapping("/remove_apply_by_order_status")
  @ResponseBody
  public String removeApplyByOrderStatus(Short orderStatus) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("orderStatus", orderStatus);
    return String.valueOf(applyService.removeApplyByCond(input));
  }
}
