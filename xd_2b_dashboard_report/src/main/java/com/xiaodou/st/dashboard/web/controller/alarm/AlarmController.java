package com.xiaodou.st.dashboard.web.controller.alarm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.enums.AlarmLevelEnum;
import com.xiaodou.st.dashboard.constants.enums.AlarmTypeEnum;
import com.xiaodou.st.dashboard.constants.enums.StatusEnum;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDTO;
import com.xiaodou.st.dashboard.domain.alarm.RawDataFacePortraitVO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.alarm.AlarmService;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.service.score.ScoreService;
import com.xiaodou.st.dashboard.service.student.StudentService;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("alarmController")
@RequestMapping("/alarm")
public class AlarmController extends BaseController {

  @Resource
  AlarmService alarmService;
  @Resource
  StudentService studentService;
  @Resource
  ScoreService scoreService;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  /**
   * 报警列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param alarmRecord
   * @return
   */
  /*@RequestMapping("/alarm_list")
  public ModelAndView alarmList(AlarmRecordDTO alarmRecordDTO) {
    ModelAndView mv = new ModelAndView("/alarm/alarmList");
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("alarmRecordDTO", alarmRecordDTO);
    try {
      mv.addObject("listAlarmRecord", alarmService.listAlarmRecord(alarmRecordDTO));
      // select下拉框数据
      mv.addObject("alarmLevelEnumList", AlarmLevelEnum.values());
      // mv.addObject("alarmTypeEnumList", AlarmTypeEnum.values());
      mv.addObject("alarmTypeEnumList", AlarmTypeEnum.getAddAlermTypeEnum());
      mv.addObject("statusEnumList", StatusEnum.values());
    } catch (Exception e) {
      LoggerUtil.error("报警列表出错！", e);
    }
    return mv;
  }*/
  @RequestMapping("/alarm_list")
  public ModelAndView alarmList(AlarmRecordDTO alarmRecordDTO) {
    ModelAndView mv = new ModelAndView("/alarm/alarmList");
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("alarmRecordDTO", alarmRecordDTO);
    try {
//      mv.addObject("listAlarmRecord", alarmService.listAlarmRecord(alarmRecordDTO));
      // select下拉框数据
      mv.addObject("alarmLevelEnumList", AlarmLevelEnum.values());
      mv.addObject("alarmTypeEnumList", AlarmTypeEnum.getAddAlermTypeEnum());
      mv.addObject("statusEnumList", StatusEnum.values());
      
      Page<AlarmRecordDO> pg = new Page<AlarmRecordDO>();
      Integer pageNo = alarmRecordDTO.getPageNo();
      if (pageNo == null) {
        pageNo = 1;
        alarmRecordDTO.setPageNo(pageNo);
      }
      pg.setPageNo(pageNo);
      Integer pageSize = alarmRecordDTO.getPageSize();
      if (null == pageSize) pageSize = 10;
      pg.setPageSize(pageSize);
      mv.addObject("adminUser", super.getAdminUser());
      Page<AlarmRecordDO> page = alarmService.listAlarmRecord(alarmRecordDTO, pg);
      if (null == page) return mv;
      mv.addObject("totalPage", page.getTotalPage());
      mv.addObject("totalCount", page.getTotalCount());
      mv.addObject("pageNo", page.getPageNo());
      mv.addObject("pageSize", pageSize);
      mv.addObject("listAlarmRecord", page.getResult());
      mv.addObject("listSize",page.getResult().size() );
      mv.addObject("alarmRecordDTO", alarmRecordDTO);
    } catch (Exception e) {
      LoggerUtil.error("报警列表出错！", e);
    }
    return mv;
  }
  

  /**
   * 人脸识别列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param testId
   * @return
   */
  @RequestMapping("/raw_data_list")
  public ModelAndView listRawData(Integer alarmId) {
    ModelAndView mv = new ModelAndView("/alarm/rawDataList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    AlarmRecordDO alarmRecord = alarmService.getAlarmRecord(alarmId);
    mv.addObject("alarmRecord", alarmRecord);
    if (null != alarmRecord) {
      mv.addObject("studentDO", studentService.getStudent(alarmRecord.getStudentId()));
      mv.addObject("listRawDataFaceRecognition",
          alarmService.listRawDataFaceRecognition(alarmRecord.getTriggerId()));
    }
    alarmService.sendWebMessage(adminUser.getRole(), adminUser.getUnitId());
    return mv;
  }

  @RequestMapping("/slide")
  public ModelAndView slide(Integer alarmId) {
    ModelAndView mv = new ModelAndView("/alarm/slide");
    AlarmRecordDO alarmRecord = alarmService.getAlarmRecord(alarmId);
    if (null != alarmRecord) {
      List<RawDataFacePortraitVO> listVO =
          alarmService.listRawDataFacePortraitVO(alarmRecord.getTriggerId());
      mv.addObject("pJson", FastJsonUtil.toJson(listVO));
      mv.addObject("count", listVO.size());
    }
    return mv;
  }

  /**
   * 
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param testId
   * @return
   */
  @RequestMapping("/login_info")
  public ModelAndView listLoginInfo(Integer alarmId) {
    ModelAndView mv = new ModelAndView("/alarm/loginInfoList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    AlarmRecordDO alarmRecord = alarmService.getAlarmRecord(alarmId);
    mv.addObject("alarmRecord", alarmRecord);
    mv.addObject("isLearnAlerm", "0");
    if (Integer.parseInt(alarmRecord.getAlarmType().getCode()) >= 11
        && Integer.parseInt(alarmRecord.getAlarmType().getCode()) <= 13) {
      mv.addObject("isLearnAlerm", "1");

      // 当前考期内预警账号的所报名的全部课程的成绩列表
      StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
      ScoreDO scoreDO = new ScoreDO();
      scoreDO.setExamDate(staticInfo.getExamDate());
      scoreDO.setStudentId(alarmRecord.getStudentId());

      Page<ScoreDO> page = scoreService.listScore(scoreDO, null);
       if (null == page) return mv;
       mv.addObject("listScore", page.getResult());
    }

    if (null != alarmRecord) {
      mv.addObject("studentDO", studentService.getStudent(alarmRecord.getStudentId()));
      mv.addObject("listLoginInfo", alarmService.listLoginInfo(alarmRecord));
    }
    alarmService.sendWebMessage(adminUser.getRole(), adminUser.getUnitId());
    return mv;
  }

}
