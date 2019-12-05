package com.xiaodou.st.dataclean.task.learnrecord.learnpercent;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
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
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserLearnRecordJob", type = MessageType.Multiple)
public class SessionLearnPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 三种角色维度下的学习时间-活跃度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try{
      LockFactory.getSessionLearnLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      CommonService commonService = SpringWebContextHolder.getBean("commonService");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataLearnRecordModel rawDataLearnRecordModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      RealSqlSession.setRealSqlSession(rawDataLearnRecordModel.getModuleId());
      pilotUnit(commonService, dashBoardServiceFacade, rawDataLearnRecordModel);
      taughtUnit(commonService, dashBoardServiceFacade, rawDataLearnRecordModel);
      unit(commonService, dashBoardServiceFacade, rawDataLearnRecordModel);
    }finally{
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
    LoggerUtil.error("记录三种角色维度下的学习时间活跃度失败", t);
  }

  // 试点单位逻辑
  public void pilotUnit(CommonService commonService, DashBoardServiceFacade dashBoardServiceFacade,
      RawDataLearnRecordModel rawDataLearnRecordModel) {
 
    // 根据条件查询学生总数
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount =
        commonService.getPilotUnitStudentCount(rawDataLearnRecordModel.getPilotUnitId().toString());

    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTime", rawDataLearnRecordModel.getRecordTime());
    input4.put("pilotUnitId", rawDataLearnRecordModel.getPilotUnitId());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    Integer activeStudentCount = dashBoardServiceFacade.queryRawStudentCountByCond(input4);
    if (studentCount == 0) {
      return;
    }
    Double avgActiveactiveStudent = (double) activeStudentCount / studentCount;
//    Double avgLearnTime = ( (double)learnTimeCount / activeStudentCount);
    Double avgLearnTime = ( (double)learnTimeCount / studentCount);
    // 是否插入或者修改
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveactiveStudent);
    // 查询记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataLearnRecordModel.getRecordTime());
    input2.put("unitId", rawDataLearnRecordModel.getPilotUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataLearnRecordModel.getPilotUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataLearnRecordModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
  }

  // 自考办逻辑
  public void taughtUnit(CommonService commonService,
      DashBoardServiceFacade dashBoardServiceFacade, RawDataLearnRecordModel rawDataLearnRecordModel) {

    // 根据条件查询学生总数
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount = commonService.getTaughtUnitStudentCount();
    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTime", rawDataLearnRecordModel.getRecordTime());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    Integer activeStudentCount = dashBoardServiceFacade.queryRawStudentCountByCond(input4);
    if (studentCount == 0) {
      return;
    }
    Double avgActiveactiveStudent = (double) activeStudentCount / studentCount;
//    Double avgLearnTime =  ((double)learnTimeCount / activeStudentCount);
    Double avgLearnTime =  ((double)learnTimeCount / studentCount);
    // 是否插入或者修改
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveactiveStudent);
    // 查询每日学习记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataLearnRecordModel.getRecordTime());
    input2.put("unitId", rawDataLearnRecordModel.getTaughtUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataLearnRecordModel.getTaughtUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataLearnRecordModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
  }

  // 主考院校逻辑
  public void unit(CommonService commonService, DashBoardServiceFacade dashBoardServiceFacade,
      RawDataLearnRecordModel rawDataLearnRecordModel) {

    // 查询主考院校旗下专业
    SessionLearnPercentModel s = new SessionLearnPercentModel();
    Integer studentCount =
        commonService.unitStundetCount(rawDataLearnRecordModel.getChiefUnitId().toString());
    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTime", rawDataLearnRecordModel.getRecordTime());
    input4.put("chiefUnitId", rawDataLearnRecordModel.getChiefUnitId());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    Integer activeStudentCount = dashBoardServiceFacade.queryRawStudentCountByCond(input4);
    if (studentCount == 0) {
      return;
    }
    Double avgActiveactiveStudent = (double) activeStudentCount / studentCount;
//    Double avgLearnTime =  ((double)learnTimeCount / activeStudentCount);
    Double avgLearnTime =  ((double)learnTimeCount / studentCount);
    // 是否插入或者修改
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveactiveStudent);
    // 查询每日学习记录是否存在
    List<SessionLearnPercentModel> dList = Lists.newArrayList();
    Map<String, Object> input2 = new HashMap<>();
    input2.put("dateTime", rawDataLearnRecordModel.getRecordTime());
    input2.put("unitId", rawDataLearnRecordModel.getChiefUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
    dList = dashBoardServiceFacade.getSessionLearnPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editSessionLearnPercent(cond, s);
    } else {
      s.setUnitId(rawDataLearnRecordModel.getChiefUnitId());
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setDateTime(rawDataLearnRecordModel.getRecordTime());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
      dashBoardServiceFacade.addSessionLearnPercent(s);
    }
    // 根据条件查询学生总数

  }
}
