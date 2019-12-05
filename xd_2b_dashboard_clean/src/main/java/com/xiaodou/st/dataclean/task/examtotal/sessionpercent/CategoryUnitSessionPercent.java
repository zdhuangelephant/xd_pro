package com.xiaodou.st.dataclean.task.examtotal.sessionpercent;

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
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategoryUnitSessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserExamTotalEventJob", type = MessageType.Multiple)
public class CategoryUnitSessionPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 试点单位-专业维度下的学期正确率
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataLearnRecordModel rawDataExamTotalModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      // 根据平均正确率
      RealSqlSession.setRealSqlSession(rawDataExamTotalModel.getModuleId());
      Map<String, Object> input4 = new HashMap<>();
      input4.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
      Double avgRightPercent = dashBoardServiceFacade.queryAvgRightPercent(input4);
      // 是否插入或者修改
      if(avgRightPercent==0)return;
      CategoryUnitSessionPercentModel s = new CategoryUnitSessionPercentModel();
      s.setRightPercent(avgRightPercent);
      // 查询记录是否存在
      Map<String, Object> input2 = new HashMap<>();
      input2.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
      input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
      List<CategoryUnitSessionPercentModel> dList =
          dashBoardServiceFacade.getCategoryUnitSessionPercentModelByCond(input2);
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
        cond.put("catId", rawDataExamTotalModel.getProductCategoryId());
        dashBoardServiceFacade.editCategoryUnitSessionPercentModel(cond, s);
      } else {
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setCatId(Integer.parseInt(rawDataExamTotalModel.getProductCategoryId()));
        s.setPilotUnitId(rawDataExamTotalModel.getPilotUnitId());

        s.setUnitId(rawDataExamTotalModel.getTaughtUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);

        s.setUnitId(rawDataExamTotalModel.getChiefUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);

        s.setUnitId(rawDataExamTotalModel.getPilotUnitId());
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);
      }
    } finally {
      LockFactory.returnCategoryUnitSessionLock();
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
    LoggerUtil.error("记录试点单位专业维度下的学期正确率失败", t);
  }
}
