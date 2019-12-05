package com.xiaodou.st.dashboard.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StudentCountVO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.manage.ManageApplyCollectService;
import com.xiaodou.st.dashboard.service.student.StudentService;
import com.xiaodou.st.dashboard.util.ExceptionWrapper;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("manageApplyCollectController")
@RequestMapping("/manage")
public class ManageApplyCollectController {

  @Resource
  ManageApplyCollectService manageApplyCollectService;

  @Resource
  StudentService studentService;


  @Resource
  IStServiceFacade stServiceFacade;

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2018年3月7日
   * @return
   */
  @RequestMapping("/student_count")
  public ModelAndView studentCount() {
    ModelAndView modelAndView = new ModelAndView("/manage/studentcount/studentcount");
    Page<StudentDO> studentPage = studentService.findStudentCountListGByPilotUnit();
    if (null == studentPage || CollectionUtils.isEmpty(studentPage.getResult())) {
      return modelAndView;
    }
    /* 学生总人数 */
    Integer studentCount = 0;
    /* 已缴费报名学生人数 */
    Integer payStudentCount = 0;
    /* 已缴费报名信息(科次) */
    Integer payApplyCount = 0;
    Map<Long, StudentCountVO> pilotUnitMap = Maps.newHashMap();
    for (StudentDO sdo : studentPage.getResult()) {
      StudentCountVO vo = new StudentCountVO();
      vo.setPilotUnitId(sdo.getPilotUnitId());
      vo.setPilotUnitName(sdo.getPilotUnitName());
      vo.setStudentCount(sdo.getStudentCount());
      pilotUnitMap.put(sdo.getPilotUnitId(), vo);
      studentCount += sdo.getStudentCount();
    }

    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("orderStatus", Constants.ALREADYPAYMENT);
    List<String> groups = Lists.newArrayList();
    groups.add("pilotUnitId");
    Page<ApplyCountDO> applyPage =
        stServiceFacade.groupCatApply(inputs, CommUtil.getAllField(ApplyCountDO.class), groups);
    if (null != applyPage && !CollectionUtils.isEmpty(applyPage.getResult())) {
      for (ApplyCountDO acdo : applyPage.getResult()) {
        if (pilotUnitMap.containsKey(acdo.getPilotUnitId())) {
          StudentCountVO studentCountVO = pilotUnitMap.get(acdo.getPilotUnitId());
          if (null != studentCountVO) {
            studentCountVO.setPayStudentCount(acdo.getStudentNum());
            studentCountVO.setPayApplyCount(acdo.getApplyCount());
            payStudentCount += acdo.getStudentNum();
            payApplyCount += acdo.getApplyCount();
          }
        }
      }
    }

    List<StudentCountVO> studentCountVOList = Lists.newArrayList(pilotUnitMap.values());
    StudentCountVO vo = new StudentCountVO();
    vo.setPilotUnitName("总计");
    vo.setStudentCount(studentCount);
    vo.setPayStudentCount(payStudentCount);
    vo.setPayApplyCount(payApplyCount);
    studentCountVOList.add(0, vo);
    modelAndView.addObject("studentCountVOList", studentCountVOList);
    return modelAndView;
  }

  /**
   * @description 报名汇总
   * @author 李德洪
   * @Date 2018年3月7日
   * @return
   */
  @RequestMapping("/apply_collect")
  public ModelAndView applyCollect() {
    ModelAndView mv = new ModelAndView("/manage/applycollect/applycollect");
    try {
      mv.addObject("applyCollectVOList", manageApplyCollectService.listApplyCollectVO());
      mv.addObject("studentCollectVOList", manageApplyCollectService.listStudentCollectVO());
    } catch (Exception e) {
      LoggerUtil.error("applyCollect", e);
      ExceptionWrapper.getWrapper().setValue(e);
      throw e;
    }
    return mv;
  }

  @RequestMapping("/download_studentcollect_excel")
  public void downloadStudentCollectVOExcel(Long pilotUnitId, Short studentStatus,
      HttpServletResponse response) throws Exception {
    try {
      String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
      manageApplyCollectService.downloadStudentCollectVOExcel("downloadStudentCollect",
        String.format("downloadStudentCollect_%s", dateFileName), pilotUnitId, studentStatus,
        response);
    } catch (Exception e) {
      LoggerUtil.error("applyCollect", e);
      ExceptionWrapper.getWrapper().setValue(e);
      throw e;
    }
  }

  @RequestMapping("/download_applycollect_excel")
  public void downloadApplyCollectVOExcel(Long pilotUnitId, Short orderStatus, Short applyStatus,
      HttpServletResponse response) throws Exception {
    try {
      String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
      manageApplyCollectService.downloadApplyCollectVOExcel("downloadApplyCollect",
        String.format("downloadApplyCollect_%s", dateFileName), pilotUnitId, orderStatus,
        applyStatus, response);
    } catch (Exception e) {
      LoggerUtil.error("applyCollect", e);
      ExceptionWrapper.getWrapper().setValue(e);
      throw e;
    }
  }
  
  
}
