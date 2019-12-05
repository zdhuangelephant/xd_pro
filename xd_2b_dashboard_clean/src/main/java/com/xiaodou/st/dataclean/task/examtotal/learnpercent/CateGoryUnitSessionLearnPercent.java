package com.xiaodou.st.dataclean.task.examtotal.learnpercent;

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
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserExamTotalEventJob", type = MessageType.Multiple)
public class CateGoryUnitSessionLearnPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 插入每日正确率逻辑
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      RawDataExamTotalModel rawDataExamTotalModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataExamTotalModel.class);
      RealSqlSession.setRealSqlSession(rawDataExamTotalModel.getModuleId());
      CategroyUnitSessionLearnPercentModel s = new CategroyUnitSessionLearnPercentModel();
      List<CategroyUnitSessionLearnPercentModel> dList = Lists.newArrayList();
      String time = DateUtil.SDF_YMD.format(System.currentTimeMillis());
      // 查询记录是否存在
      Map<String, Object> input2 = new HashMap<>();
      input2.put("dateTime", time);
      input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
      input2.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      dList = dashBoardServiceFacade.getCategroySessionLearnPercentModelByCond(input2);
      
      // 根据平均正确率
      Map<String, Object> input4 = new HashMap<>();
      input4.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
      Double avgRightPercent = dashBoardServiceFacade.queryAvgRightPercent(input4);
      
      s.setRightPercent(avgRightPercent);
      // 是否插入或者修改
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("dateTime", time);
        cond.put("catId", rawDataExamTotalModel.getProductCategoryId());
        cond.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
        dashBoardServiceFacade.editCategroySessionLearnPercent(cond, s);
      } else {
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setDateTime(time);
        s.setPilotUnitId(rawDataExamTotalModel.getPilotUnitId());
        s.setCatId(Integer.valueOf(rawDataExamTotalModel.getProductCategoryId()));

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setUnitId(rawDataExamTotalModel.getTaughtUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setUnitId(rawDataExamTotalModel.getChiefUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);

        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setUnitId(rawDataExamTotalModel.getPilotUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        dashBoardServiceFacade.addCategroySessionLearnPercent(s);
      }
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
    LoggerUtil.error("记录每日正确率逻辑失败", t);
  }
}
