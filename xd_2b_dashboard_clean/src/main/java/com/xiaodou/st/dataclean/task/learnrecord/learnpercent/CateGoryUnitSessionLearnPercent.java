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
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.CategroyUnitSessionLearnPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserLearnRecordJob", type = MessageType.Multiple)
public class CateGoryUnitSessionLearnPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 试点单位-专业维度下的学习时间-活跃度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      CommonService commonService = SpringWebContextHolder.getBean("commonService");
      RawDataLearnRecordModel rawDataLearnRecordModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      RealSqlSession.setRealSqlSession(rawDataLearnRecordModel.getModuleId());
      CategroyUnitSessionLearnPercentModel s = new CategroyUnitSessionLearnPercentModel();
      // 查询学生是否存在
      List<CategroyUnitSessionLearnPercentModel> dList = Lists.newArrayList();

      // 根据条件查询 学生总数
      Integer studentCount =
          commonService.getStudentCountByCond(rawDataLearnRecordModel.getPilotUnitId(),
              rawDataLearnRecordModel.getProductCategoryId());
      // 根据条件查询学生总学习时间
      Map<String, Object> input4 = new HashMap<>();
      input4.put("pilotUnitId", rawDataLearnRecordModel.getPilotUnitId());
      input4.put("recordTime", rawDataLearnRecordModel.getRecordTime());
      input4.put("productCategoryId", rawDataLearnRecordModel.getProductCategoryId());
      Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
      Integer activeStudentCount = dashBoardServiceFacade.queryRawStudentCountByCond(input4);
      if (studentCount == 0) {
        return;
      }
      Double avgActiveactiveStudent = (double) activeStudentCount / studentCount;
//      Double avgLearnTime = ((double) learnTimeCount / activeStudentCount);
      Double avgLearnTime = ((double) learnTimeCount / studentCount);
      // 是否插入或者修改
      s.setLearnTimePercent(avgLearnTime);
      s.setLearnPercent(avgActiveactiveStudent);
      // 查询记录是否存在
      Map<String, Object> input2 = new HashMap<>();
      input2.put("dateTime", rawDataLearnRecordModel.getRecordTime());
      input2.put("catId", rawDataLearnRecordModel.getProductCategoryId());
      input2.put("pilotUnitId", rawDataLearnRecordModel.getPilotUnitId());
      dList = dashBoardServiceFacade.getCategroySessionLearnPercentModelByCond(input2);
      // 是否插入或者修改
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("dateTime", rawDataLearnRecordModel.getRecordTime());
        cond.put("catId", rawDataLearnRecordModel.getProductCategoryId());
        cond.put("pilotUnitId", rawDataLearnRecordModel.getPilotUnitId());
        dashBoardServiceFacade.editCategroySessionLearnPercent(cond, s);
      } else {
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setDateTime(rawDataLearnRecordModel.getRecordTime());
        s.setPilotUnitId(rawDataLearnRecordModel.getPilotUnitId());
        s.setCatId(Integer.valueOf(rawDataLearnRecordModel.getProductCategoryId()));

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setUnitId(rawDataLearnRecordModel.getTaughtUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setUnitId(rawDataLearnRecordModel.getChiefUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setUnitId(rawDataLearnRecordModel.getPilotUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);
      }
      QueueService queueService = SpringWebContextHolder.getBean("queueService");
      queueService.pushAfterLearnRecord(rawDataLearnRecordModel);
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
    LoggerUtil.error("记录试点单位专业维度下的学习时间活跃度失败", t);
  }
}
