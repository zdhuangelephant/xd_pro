package com.xiaodou.st.dashboard.service.apply;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ApplyService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ManageStaticInfoService manageStaticInfoService;
  @Resource
  ClassService classService;

  public List<ApplyDO> listApply(ApplyDO applyDO) {
    Page<ApplyDO> page =  listApply(applyDO, null,null);
    if(null == page || null == page.getResult() || page.getResult().isEmpty()) return null;
    return page.getResult();
  }
  public Page<ApplyDO> listApply(ApplyDO applyDO,Page<ApplyDO> pg) {
    return listApply(applyDO, null,pg);
  }

  public Page<ApplyDO> listApply(ApplyDO applyDO, List<String> studentIdList,Page<ApplyDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    INPUTS: {
      if (null == applyDO) break INPUTS;
      if (null != applyDO.getOrderStatus()) inputs.put("orderStatus", applyDO.getOrderStatus());
      if (null != applyDO.getApplyStatus()) inputs.put("applyStatus", applyDO.getApplyStatus());
      if (StringUtils.isNotBlank(applyDO.getExamDate()))
        inputs.put("examDate", applyDO.getExamDate());
      if (null != applyDO.getCatId()) inputs.put("catId", applyDO.getCatId());
      if (null != applyDO.getProductId()) inputs.put("productId", applyDO.getProductId());
      if (null != applyDO.getClassId()) inputs.put("classId", applyDO.getClassId());
      if (StringUtils.isNotBlank(applyDO.getStudentName()))
        inputs.put("studentNameLike", applyDO.getStudentName());
      if (StringUtils.isNotBlank(applyDO.getAdmissionCardCode()))
        inputs.put("admissionCardCodeLike", applyDO.getAdmissionCardCode());
      if (StringUtils.isNotBlank(applyDO.getTelephone()))
        inputs.put("telephoneLike", applyDO.getTelephone());
      if (null != applyDO.getOrderNumber()) inputs.put("orderNumber", applyDO.getOrderNumber());
      if (null != applyDO.getStudentId()) inputs.put("studentId", applyDO.getStudentId());
      if (null != studentIdList && studentIdList.size() > 0)
        inputs.put("studentIdList", studentIdList);

      if (null != adminUser.getChildRole()
          && Constants.POILT_UNIT_CHILD_ROLE.equals(adminUser.getChildRole())) {
        List<ClassDO> classList = classService.listClass();
        List<Long> classIdList = Lists.transform(classList, new Function<ClassDO, Long>() {
          @Override
          public Long apply(ClassDO input) {
            if (null == input.getId()) return 0l;
            return input.getId();
          }
        });
        if(CollectionUtils.isEmpty(classIdList)){
          return null;
        }
        inputs.put("classIdList", classIdList);
      }
    }
    return stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),pg);
  }

  public List<ApplyCountDO> groupCatApply(ApplyCountDO applyDO) {
    Map<String, Object> inputs = Maps.newHashMap();
    List<String> groups = Lists.newArrayList();
    INPUTS: {
      if (null == applyDO) break INPUTS;
      if (null != applyDO.getOrderStatus()) inputs.put("orderStatus", applyDO.getOrderStatus());
      if (null != applyDO.getApplyStatus()) inputs.put("applyStatus", applyDO.getApplyStatus());
      if (StringUtils.isNotBlank(applyDO.getExamDate()))
        inputs.put("examDate", applyDO.getExamDate());
      if (null != applyDO.getCatId()) inputs.put("catId", applyDO.getCatId());
      if (null != applyDO.getPilotUnitId()) inputs.put("pilotUnitId", applyDO.getPilotUnitId());
      if (null != applyDO.getProductId()) inputs.put("productId", applyDO.getProductId());
      if (null != applyDO.getClassId()) inputs.put("classId", applyDO.getClassId());
      if (null != applyDO.getOrderNumber()) inputs.put("orderNumber", applyDO.getOrderNumber());
    }
    groups.add("catId");
    Page<ApplyCountDO> page =
        stServiceFacade.groupCatApply(inputs, CommUtil.getAllField(ApplyCountDO.class), groups);
    if (null == page) return null;
    return page.getResult();
  }

  public List<ApplyDO> listApplyByCond(Map<String, Object> inputs) {
    Page<ApplyDO> page = stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),null);
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 某专业报名成功的用户
   * @author 李德洪
   * @Date 2017年8月9日
   * @param catId
   * @return
   */
  public List<Integer> listSuccessApplyByCid(Integer catId) {
    List<Integer> studentList = Lists.newArrayList();
    Map<String, Object> inputs = Maps.newHashMap();
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    inputs.put("examDate", staticInfo.getExamDate());
    inputs.put("applyStatus", 1);
    inputs.put("orderStatus", 1);
    inputs.put("catId", catId);
    List<ApplyDO> applyList = this.listApplyByCond(inputs);
    if (null == applyList || applyList.isEmpty()) return null;
    for (ApplyDO apply : applyList) {
      if (!studentList.contains(apply.getStudentId())) studentList.add(apply.getStudentId());
    }
    return studentList;
  }

  /**
   * 
   * @description 根据id组成的字符串查询报名信息
   * @author 李德洪
   * @Date 2017年6月22日
   * @param applyIds
   * @return
   */
  public List<ApplyDO> listApplyByIds(String applyIds) {
    Map<String, Object> inputs = Maps.newHashMap();
    List<String> idList = Arrays.asList(applyIds.split(","));
    inputs.put("idList", idList);
    Page<ApplyDO> page = stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),null);
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 根據學生id查詢報名列表
   * @author 李德洪
   * @Date 2017年4月4日
   * @param studentId
   * @return
   */
  public List<ApplyDO> listApplyBySid(Integer studentId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentId", studentId);
    Page<ApplyDO> page = stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),null);
    if (null == page) return null;
    return page.getResult();
  }


  public List<ApplyDO> listApplyBySidList(@SuppressWarnings("rawtypes") List studentIdList) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentIdList", studentIdList);
    Page<ApplyDO> page = stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),null);
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 增加报名
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param applyDO
   * @return
   */
  public Boolean saveApply(ApplyDO applyDO) {
    return stServiceFacade.saveApply(applyDO);
  }

  /**
   * 增加报名
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param applyDO
   * @return
   */
  public Integer saveApplyList(List<ApplyDO> applyDOList) {
    return stServiceFacade.saveApplyList(applyDOList);
  }

  /**
   * 批量修改报名状态
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param inputs
   * @param applyDO
   * @return
   */
  public boolean updateApply(Map<String, Object> inputs, ApplyDO applyDO) {
    return stServiceFacade.updateApplyByCond(inputs, applyDO);
  }

  /**
   * 批量刪除报名信息
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param inputs
   * @return
   */
  public Integer removeApplyByCond(Map<String, Object> inputs) {
    return stServiceFacade.removeApplyByCond(inputs);
  }

  public boolean removeApply(Long applyId) {
    return stServiceFacade.removeApply(applyId);
  }

  /**
   * 
   * @description 根據學生id，查詢報考專業
   * @author 李德洪
   * @Date 2017年8月4日
   * @param studentId
   * @return
   */
  public List<ApplyDO> getMajorByStuId(Integer studentId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentId", studentId);
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("catId", "");
    outputs.put("catName", "");
    Page<ApplyDO> listApply = stServiceFacade.listApply(inputs, outputs,null);
    if (listApply != null) {
      return listApply.getResult();
    }
    return null;
  }
}
