package com.xiaodou.st.dataclean.task.examtotal.avgscore;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xiaodou.st.dataclean.model.domain.dashboard.CateGoryUnitProductAvgScoreModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserExamTotalEventJob", type = MessageType.Multiple)
public class CateGoryUnitProductAvgScorePercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 课程平均分记录逻辑 
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitProductAvgScorePercent();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataLearnRecordModel rawDataExamTotalModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      // 平均分
      RealSqlSession.setRealSqlSession(rawDataExamTotalModel.getModuleId());
      Map<String, Object> input4 = new HashMap<>();
      input4.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      input4.put("productId", rawDataExamTotalModel.getProductId());
      input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
      Double avgScore = dashBoardServiceFacade.queryAvgScore(input4);
      // 是否插入或者修改
      CateGoryUnitProductAvgScoreModel s = new CateGoryUnitProductAvgScoreModel();
      s.setAvgScore(avgScore);
      // 查询记录是否存在
      Map<String, Object> input2 = new HashMap<>();
      input2.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
      input2.put("productId", rawDataExamTotalModel.getProductId());
      List<CateGoryUnitProductAvgScoreModel> dList =
          dashBoardServiceFacade.getCateGoryUnitProductAvgScoreModelByCond(input2);
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
        cond.put("catId", rawDataExamTotalModel.getProductCategoryId());
        cond.put("productId", rawDataExamTotalModel.getProductId());
        dashBoardServiceFacade.editCateGoryUnitProductAvgScoreModel(cond, s);
      } else {
        s.setProductId(Integer.valueOf(rawDataExamTotalModel.getProductId()));
        s.setPilotUnitId(rawDataExamTotalModel.getPilotUnitId());
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setCatId(Integer.parseInt(rawDataExamTotalModel.getProductCategoryId()));

        s.setUnitId(rawDataExamTotalModel.getTaughtUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addcateGoryUnitProductAvgScoreDao(s);

        s.setUnitId(rawDataExamTotalModel.getChiefUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addcateGoryUnitProductAvgScoreDao(s);

        s.setUnitId(rawDataExamTotalModel.getPilotUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addcateGoryUnitProductAvgScoreDao(s);
      }
    } finally {
      LockFactory.returnCategoryUnitProductAvgScorePercent();
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
    LoggerUtil.error("记录课程平均分失败", t);
  }
}
