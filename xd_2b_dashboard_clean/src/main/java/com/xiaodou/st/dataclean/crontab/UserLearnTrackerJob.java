package com.xiaodou.st.dataclean.crontab;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.enums.AlarmLevelEnum;
import com.xiaodou.st.dataclean.enums.AlarmTypeEnum;
import com.xiaodou.st.dataclean.enums.PretreatmentEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.StudentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.LearnRecordDO;
import com.xiaodou.st.dataclean.model.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name UserLearnActionJob CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年3月5日
 * @description TODO
 * @version 1.0
 */
public class UserLearnTrackerJob {
  private String module;
  private DashBoardServiceFacade dashBoardServiceFacade;
  private QueueService queueService;

  public UserLearnTrackerJob(String module) {
    this.module = module;
  }

  public void work() {
    dashBoardServiceFacade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    queueService = SpringWebContextHolder.getBean("queueService");
    ArrayList<AlarmRecordModel> alarmBulks = Lists.newArrayList();
    StaticInfoDO staticInfo = dashBoardServiceFacade.staticInfo();

    Date nowDate = new Date();
    try {
      if (nowDate.getTime() > DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()).getTime())
        return;
    } catch (Exception e) {}

    // item1: 有学习记录的学生
    List<LearnRecordDO> learnRecordList = dashBoardServiceFacade.getStudentForMaxLearnTime(null);

    // item2: 报名成功却一直没有学习过的学生
    // stuId、 recordTime
    List<Integer> neverLearnStuList = dashBoardServiceFacade.getStudentForNeverLearnCourse();
    ArrayList<LearnRecordDO> neverLearnList = Lists.newArrayList();
    if (neverLearnStuList != null && neverLearnStuList.size() != 0)
      for (Integer worstStuId : neverLearnStuList) {
        LearnRecordDO learnDO = new LearnRecordDO();
        learnDO.setStudentId(worstStuId);
        try {
          learnDO.setRecordTime(DateUtil.SDF_YMD.parse(staticInfo.getBeginApplyTime()));
        } catch (Exception e) {}
        neverLearnList.add(learnDO);
      }
    learnRecordList.addAll(neverLearnList);


    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, -1);

    if (!CollectionUtils.isEmpty(learnRecordList)) {
      for (LearnRecordDO learnRecord : learnRecordList) {
        AlarmRecordModel alarmRecord = new AlarmRecordModel();
        String recordTime = DateUtil.SDF_YMD.format(learnRecord.getRecordTime());
        Integer dValue = 0;
        try {
          dValue = DateUtil.getDiffDays(recordTime, DateUtil.SDF_YMD.format(c.getTime()));
        } catch (ParseException e) {}
        if (dValue >= 7 && dValue < 14) {
          alarmRecord.setAlarmLevel(AlarmLevelEnum.AlarmLevel_1.name());
          alarmRecord.setAlarmType(AlarmTypeEnum.AlarmType_11.name());
        } else if (dValue >= 14 && dValue < 21) {
          alarmRecord.setAlarmLevel(AlarmLevelEnum.AlarmLevel_2.name());
          alarmRecord.setAlarmType(AlarmTypeEnum.AlarmType_12.name());
        } else if (dValue >= 21) {
          alarmRecord.setAlarmLevel(AlarmLevelEnum.AlarmLevel_3.name());
          alarmRecord.setAlarmType(AlarmTypeEnum.AlarmType_13.name());
        } else {
          continue;
        }

        alarmRecord.setModule(module);
        alarmRecord.setReadStatus((short) 0);
        alarmRecord.setStatus("Status_1");
        alarmRecord.setAlarmTime(new Timestamp(System.currentTimeMillis()));
        alarmRecord.setStudentId(learnRecord.getStudentId());
        alarmRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        alarmRecord.setTriggerType((short) 2);
        alarmRecord.setPretreatment(PretreatmentEnum.ResponseNothing.name());

        // 自考办
        AlarmRecordModel selfGov = new AlarmRecordModel();
        selfGov.setModule(alarmRecord.getModule());
        selfGov.setStudentId(alarmRecord.getStudentId());
        selfGov.setTriggerType(alarmRecord.getTriggerType());
        selfGov.setAlarmLevel(alarmRecord.getAlarmLevel());
        selfGov.setAlarmType(alarmRecord.getAlarmType());
        selfGov.setStatus(alarmRecord.getStatus());
        selfGov.setPretreatment(alarmRecord.getPretreatment());
        selfGov.setAlarmTime(alarmRecord.getAlarmTime());
        selfGov.setReadStatus(alarmRecord.getReadStatus());
        selfGov.setCreateTime(alarmRecord.getCreateTime());

        selfGov.setRoleType(Constant.SELF_TAUGHT_UNIT_ROLE);
        selfGov.setUnitId(Constant.SELF_TAUGHT_UNIT_ID);
        alarmBulks.add(selfGov);
        // 试点单位
        AlarmRecordModel poiltUnit = new AlarmRecordModel();
        poiltUnit.setModule(alarmRecord.getModule());
        poiltUnit.setStudentId(alarmRecord.getStudentId());
        poiltUnit.setTriggerType(alarmRecord.getTriggerType());
        poiltUnit.setAlarmLevel(alarmRecord.getAlarmLevel());
        poiltUnit.setAlarmType(alarmRecord.getAlarmType());
        poiltUnit.setStatus(alarmRecord.getStatus());
        poiltUnit.setPretreatment(alarmRecord.getPretreatment());
        poiltUnit.setAlarmTime(alarmRecord.getAlarmTime());
        poiltUnit.setReadStatus(alarmRecord.getReadStatus());
        poiltUnit.setCreateTime(alarmRecord.getCreateTime());
        StudentModel sm = dashBoardServiceFacade.getStudentBySid(poiltUnit.getStudentId());
        if (null != sm && sm.getPilotUnitId() != null) {
          poiltUnit.setRoleType(Constant.POILT_UNIT_ROLE);
          poiltUnit.setUnitId(sm.getPilotUnitId()); //
          alarmBulks.add(poiltUnit);
        }

        // 主考院校
        AlarmRecordModel cooperCollege = new AlarmRecordModel();
        cooperCollege.setModule(alarmRecord.getModule());
        cooperCollege.setStudentId(alarmRecord.getStudentId());
        cooperCollege.setTriggerType(alarmRecord.getTriggerType());
        cooperCollege.setAlarmLevel(alarmRecord.getAlarmLevel());
        cooperCollege.setAlarmType(alarmRecord.getAlarmType());
        cooperCollege.setStatus(alarmRecord.getStatus());
        cooperCollege.setPretreatment(alarmRecord.getPretreatment());
        cooperCollege.setAlarmTime(alarmRecord.getAlarmTime());
        cooperCollege.setReadStatus(alarmRecord.getReadStatus());
        cooperCollege.setCreateTime(alarmRecord.getCreateTime());
        List<Integer> listTemp = Lists.newArrayList();
        List<ApplyModel> listApply =
            dashBoardServiceFacade.queryApplyBySid(alarmRecord.getStudentId());
        for (ApplyModel ado : listApply) {
          ChiefUnitRelationModel curm = dashBoardServiceFacade.getChiefByCatId(ado.getCatId());
          if (curm == null) continue;
          if (!listTemp.contains(curm.getUnitId())) {
            listTemp.add(curm.getUnitId());
            cooperCollege.setRoleType(Constant.CHIEF_UNIT_ROLE);
            cooperCollege.setUnitId(curm.getUnitId());
            alarmBulks.add(cooperCollege);
          }
        }
      }
    }

    for (AlarmRecordModel alarm : alarmBulks) {
      queueService.pushUserLearnTracker(alarm);
    }
  }

}
