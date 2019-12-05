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
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.CategroyUnitSessionLearnPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "FinishMissionJob", type = MessageType.Multiple)
public class CateGoryUnitSessionLearnPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 试点单位-专业维度下的任务完成度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      CommonService commonService = SpringWebContextHolder.getBean("commonService");
      RawDataFinishMissionModel rawDataFinishMissionModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataFinishMissionModel.class);
      RealSqlSession.setRealSqlSession(rawDataFinishMissionModel.getModuleId());
      CategroyUnitSessionLearnPercentModel s = new CategroyUnitSessionLearnPercentModel();
      List<CategroyUnitSessionLearnPercentModel> dList = Lists.newArrayList();
      // 根据条件查询 学生总数
      Integer studentCount =
          commonService.getStudentCountByCond(rawDataFinishMissionModel.getPilotUnitId(),
              rawDataFinishMissionModel.getProductCategoryId());
      // 根据条件查询任务完成度
      Map<String, Object> input4 = new HashMap<>();
      input4.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
      input4.put("productCategoryId", rawDataFinishMissionModel.getProductCategoryId());
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
      Map<String, Object> input2 = new HashMap<>();
      input2.put("dateTime", rawDataFinishMissionModel.getRecordTime());
      input2.put("catId", rawDataFinishMissionModel.getProductCategoryId());
      input2.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
      // 查询记录是否存在
      dList = dashBoardServiceFacade.getCategroySessionLearnPercentModelByCond(input2);
      // 是否插入或者修改
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("dateTime", rawDataFinishMissionModel.getRecordTime());
        cond.put("catId", rawDataFinishMissionModel.getProductCategoryId());
        cond.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
        dashBoardServiceFacade.editCategroySessionLearnPercent(cond, s);
      } else {
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setDateTime(rawDataFinishMissionModel.getRecordTime());
        s.setPilotUnitId(rawDataFinishMissionModel.getPilotUnitId());

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setCatId(Integer.valueOf(rawDataFinishMissionModel.getProductCategoryId()));
        s.setUnitId(rawDataFinishMissionModel.getTaughtUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setUnitId(rawDataFinishMissionModel.getChiefUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setUnitId(rawDataFinishMissionModel.getPilotUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);
      }
      QueueService queueService = SpringWebContextHolder.getBean("queueService");
      queueService.pushFinishMissionAfter(rawDataFinishMissionModel);
    } finally {
      LockFactory.returnCategoryUnitSessionLearnLock();
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
    LoggerUtil.error("记录试点单位专业维度下的任务完成度失败", t);
  }
}
