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
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategorySessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserExamTotalEventJob", type = MessageType.Multiple)
public class CategorySessionPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 三种角色维度下的学期正确率
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try{
      LockFactory.getCategorySessionLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataExamTotalModel rawDataExamTotalModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataExamTotalModel.class);
      RealSqlSession.setRealSqlSession(rawDataExamTotalModel.getModuleId());
      pilotUnit(rawDataExamTotalModel, dashBoardServiceFacade);
      taughtUnit(rawDataExamTotalModel, dashBoardServiceFacade);
      unit(rawDataExamTotalModel, dashBoardServiceFacade);
    }finally{
      LockFactory.returnCategorySessionLock();
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
    LoggerUtil.error("记录三种角色维度下的学期正确率失败", t);
  }

  // 试点单位逻辑
  public void pilotUnit(RawDataExamTotalModel rawDataExamTotalModel,
      DashBoardServiceFacade dashBoardServiceFacade) { 
    // 根据条件查询正确率
    Map<String, Object> input4 = new HashMap<>();
    input4.put("pilotUnitId", rawDataExamTotalModel.getPilotUnitId());
    input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
    Double avgRightPercent = dashBoardServiceFacade.queryAvgRightPercent(input4);
    // 是否插入或者修改
    if(avgRightPercent==0)return;
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setRightPercent(avgRightPercent);
    // 查询记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataExamTotalModel.getPilotUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
    input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataExamTotalModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      s.setUnitId(rawDataExamTotalModel.getPilotUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataExamTotalModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }

  // 自考办逻辑
  public void taughtUnit(RawDataExamTotalModel rawDataExamTotalModel,
      DashBoardServiceFacade dashBoardServiceFacade) { 
    // 根据条件查询正确率
    Map<String, Object> input4 = new HashMap<>();
    input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
    input4.put("taughtUnitId", rawDataExamTotalModel.getTaughtUnitId());
    Double avgRightPercent = dashBoardServiceFacade.queryAvgRightPercent(input4);
    // 是否插入或者修改
    if(avgRightPercent==0)return;
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setRightPercent(avgRightPercent);
    // 查询每日学习记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataExamTotalModel.getTaughtUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataExamTotalModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      s.setUnitId(rawDataExamTotalModel.getTaughtUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataExamTotalModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }

  // 主考院校逻辑
  public void unit(RawDataExamTotalModel rawDataExamTotalModel,
      DashBoardServiceFacade dashBoardServiceFacade) {
    // 根据条件查询正确率
    Map<String, Object> input4 = new HashMap<>();
    input4.put("productCategoryId", rawDataExamTotalModel.getProductCategoryId());
    input4.put("chiefUnitId", rawDataExamTotalModel.getChiefUnitId());
    Double avgRightPercent = dashBoardServiceFacade.queryAvgRightPercent(input4);
    // 是否插入或者修改
    if(avgRightPercent==0)return;
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setRightPercent(avgRightPercent);
    // 查询每日学习记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataExamTotalModel.getChiefUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
    input2.put("catId", rawDataExamTotalModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataExamTotalModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
      s.setUnitId(rawDataExamTotalModel.getChiefUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataExamTotalModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }
}
