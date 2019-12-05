package com.xiaodou.st.dataclean.task.mission.learnpercent;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.SessionLearnPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "FinishMissionJob", type = MessageType.Multiple)
public class SessionLearnPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 记录学习记录进度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getSessionLearnLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      CommonService commonService = SpringWebContextHolder.getBean("commonService");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataFinishMissionModel rawDataFinishMissionModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataFinishMissionModel.class);
      RealSqlSession.setRealSqlSession(rawDataFinishMissionModel.getModuleId());
      pilotUnit(commonService, dashBoardServiceFacade, rawDataFinishMissionModel);
      taughtUnit(commonService, dashBoardServiceFacade, rawDataFinishMissionModel);
      unit(commonService, dashBoardServiceFacade, rawDataFinishMissionModel);
    } finally {
      LockFactory.returnSessionLearnLock();
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("记录三种角色维度下的任务完成度失败", t);
  }

  // 试点单位逻辑
  public void pilotUnit(CommonService commonService, DashBoardServiceFacade dashBoardServiceFacade,
      RawDataFinishMissionModel rawDataFinishMissionModel) {
    // 根据条件查询学生总数
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount =
        commonService.getPilotUnitStudentCount(rawDataFinishMissionModel.getPilotUnitId()
            .toString());

    // 根据条件查询学生任务完成度
    Map<String, Object> input4 = new HashMap<>();
    input4.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
    input4.put("recordTime", rawDataFinishMissionModel.getRecordTime());
    Integer currentFinishStudentCount =
        dashBoardServiceFacade.getCurrentFinishMissionStudentCount(input4);
    input4.put("createTimeUpper", new Timestamp(DateUtil.getLastTimeOfDay(rawDataFinishMissionModel.getCreateTime())));
    Integer finishStudentCount = dashBoardServiceFacade.getFinishMissionStudentCount(input4);
    if (studentCount == 0) {
      return;
    }
    Double missionPercent =
        ((double) (finishStudentCount + currentFinishStudentCount) / studentCount);
    s.setMissionPercent(missionPercent);
    // 是否插入或者修改
    // 查询记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataFinishMissionModel.getRecordTime());
    input2.put("unitId", rawDataFinishMissionModel.getPilotUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataFinishMissionModel.getPilotUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataFinishMissionModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
  }

  // 自考办逻辑
  public void taughtUnit(CommonService commonService,
      DashBoardServiceFacade dashBoardServiceFacade,
      RawDataFinishMissionModel rawDataFinishMissionModel) {
    // 根据条件查询学生总数
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount = commonService.getTaughtUnitStudentCount();
    // 根据条件查询学生任务完成度
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTime", rawDataFinishMissionModel.getRecordTime());
    Integer currentFinishStudentCount =
        dashBoardServiceFacade.getCurrentFinishMissionStudentCount(input4);
    input4.put("createTimeUpper", new Timestamp(DateUtil.getLastTimeOfDay(rawDataFinishMissionModel.getCreateTime())));
    Integer finishStudentCount = dashBoardServiceFacade.getFinishMissionStudentCount(input4);
    if (studentCount == 0) {
      return;
    }
    Double missionPercent =
        ((double) (finishStudentCount + currentFinishStudentCount) / studentCount);
    s.setMissionPercent(missionPercent);
    // 查询每日学习记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataFinishMissionModel.getRecordTime());
    input2.put("unitId", rawDataFinishMissionModel.getTaughtUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataFinishMissionModel.getTaughtUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataFinishMissionModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
  }

  // 主考院校逻辑
  public void unit(CommonService commonService, DashBoardServiceFacade dashBoardServiceFacade,
      RawDataFinishMissionModel rawDataFinishMissionModel) {

    // 查询主考院校旗下专业
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount =
        commonService.unitStundetCount(rawDataFinishMissionModel.getChiefUnitId().toString());
    // 根据条件查询学生任务完成度
    Map<String, Object> input4 = new HashMap<>();
    input4.put("chiefUnitId", rawDataFinishMissionModel.getChiefUnitId());
    input4.put("recordTime", rawDataFinishMissionModel.getRecordTime());
    Integer currentFinishStudentCount =
        dashBoardServiceFacade.getCurrentFinishMissionStudentCount(input4);
    input4.put("createTimeUpper", new Timestamp(DateUtil.getLastTimeOfDay(rawDataFinishMissionModel.getCreateTime())));
    Integer finishStudentCount = dashBoardServiceFacade.getFinishMissionStudentCount(input4);
    if (studentCount == 0) {
      return;
    }
    Double missionPercent =
        ((double) (finishStudentCount + currentFinishStudentCount) / studentCount);
    s.setMissionPercent(missionPercent);
    // 查询每日学习记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataFinishMissionModel.getRecordTime());
    input2.put("unitId", rawDataFinishMissionModel.getChiefUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataFinishMissionModel.getChiefUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataFinishMissionModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
  }
}
