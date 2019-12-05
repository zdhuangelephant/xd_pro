package com.xiaodou.st.dataclean.task.learnrecord.sessionpercent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategorySessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductModel;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "AfterUserLearnRecord", type = MessageType.Multiple)
public class CategorySessionPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;


  /**
   * 三种角色下学期平均学习时间活跃度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
        LockFactory.getCategorySessionLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      CommonService commonService = SpringWebContextHolder.getBean("commonService");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataLearnRecordModel rawDataLearnRecordModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      // 获取产品时间间隔
      RealSqlSession.setRealSqlSession(rawDataLearnRecordModel.getModuleId());
      RawDataProductModel product =
          dashBoardServiceFacade.getRawDataProductById(Long.parseLong(rawDataLearnRecordModel
              .getProductId()));
      String begin = DateUtil.SDF_YMD.format(product.getBeginApplyTime());
      String end = DateUtil.SDF_YMD.format(product.getEndApplyTime());
      int day =(int) ((product.getEndApplyTime().getTime() - product.getBeginApplyTime().getTime()) / (24 * 60 * 60 * 1000));
      pilotUnit(dashBoardServiceFacade, commonService, rawDataLearnRecordModel, begin, end, day);
      taughtUnit(dashBoardServiceFacade, commonService, rawDataLearnRecordModel, begin, end, day);
      unit(dashBoardServiceFacade, commonService, rawDataLearnRecordModel, begin, end, day);
    } finally {
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
    LoggerUtil.error("记录三种角色下学期平均学习时间活跃度失败", t);
  }

  // 试点单位逻辑
  public void pilotUnit(DashBoardServiceFacade dashBoardServiceFacade, CommonService commonService,
      RawDataLearnRecordModel rawDataLearnRecordModel, String begin, String end, int day) {
    // 根据条件查询学生总数
    Integer studentCount =
        commonService.getStudentCountByCond(rawDataLearnRecordModel.getPilotUnitId(),
            rawDataLearnRecordModel.getProductCategoryId());
    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTimeUpper", begin);
    input4.put("recordTimeLower", end);
    input4.put("productCategoryId", rawDataLearnRecordModel.getProductCategoryId());
    input4.put("chiefUnitId", rawDataLearnRecordModel.getChiefUnitId());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    // 根据条件查询有学习记录的学生人数
    input4.put("learnPercentNotNull", "1");
    
    if (studentCount == 0 || day == 0) {
      return;
    }
    Double avgActiveLearnPercent = dashBoardServiceFacade.queryAvgLearnActive(input4);
    Double avgLearnTime = ((double) learnTimeCount / day/studentCount);
    // 是否插入或者修改
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveLearnPercent);
    s.setStudentCount(studentCount);
    // 查询记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataLearnRecordModel.getPilotUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
    input2.put("catId", rawDataLearnRecordModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataLearnRecordModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      s.setUnitId(rawDataLearnRecordModel.getPilotUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataLearnRecordModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }

  // 自考办逻辑
  public void taughtUnit(DashBoardServiceFacade dashBoardServiceFacade,
      CommonService commonService, RawDataLearnRecordModel rawDataLearnRecordModel, String begin,
      String end, int day) {
    // 根据条件查询学生总数
    Integer studentCount =
        commonService.getStudentCountByCond(null, rawDataLearnRecordModel.getProductCategoryId());
    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTimeUpper", begin);
    input4.put("recordTimeLower", end);
    input4.put("productCategoryId", rawDataLearnRecordModel.getProductCategoryId());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    input4.put("learnPercentNotNull", "1");
    if (studentCount == 0 || day == 0) {
      return;
    }
    Double avgActiveLearnPercent = dashBoardServiceFacade.queryAvgLearnActive(input4);
    Double avgLearnTime = ((double) learnTimeCount / day/studentCount);
    // 是否插入或者修改
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveLearnPercent);
    s.setStudentCount(studentCount);
    // 查询记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataLearnRecordModel.getTaughtUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    input2.put("catId", rawDataLearnRecordModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataLearnRecordModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      s.setUnitId(rawDataLearnRecordModel.getTaughtUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataLearnRecordModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }

  // 主考院校逻辑
  public void unit(DashBoardServiceFacade dashBoardServiceFacade, CommonService commonService,
      RawDataLearnRecordModel rawDataLearnRecordModel, String begin, String end, int day) {
    // 根据条件查询学生总数
    Integer studentCount =
        commonService.getStudentCountByCond(null, rawDataLearnRecordModel.getProductCategoryId());
    // 根据条件查询学生总学习时间
    Map<String, Object> input4 = new HashMap<>();
    input4.put("recordTimeUpper", begin);
    input4.put("recordTimeLower", end);
    input4.put("productCategoryId", rawDataLearnRecordModel.getProductCategoryId());
    input4.put("pilotUnitId", rawDataLearnRecordModel.getPilotUnitId());
    Integer learnTimeCount = dashBoardServiceFacade.queryRawLearnTimeCountByCond(input4);
    input4.put("learnPercentNotNull", "1");
    if (studentCount == 0 || day == 0) {
      return;
    }
    Double avgActiveLearnPercent = dashBoardServiceFacade.queryAvgLearnActive(input4);
    Double avgLearnTime = ((double) learnTimeCount / day/studentCount);
    // 是否插入或者修改
    CategorySessionPercentModel s = new CategorySessionPercentModel();
    s.setLearnTimePercent(avgLearnTime);
    s.setLearnPercent(avgActiveLearnPercent);
    s.setStudentCount(studentCount);
    // 查询记录是否存在
    Map<String, Object> input2 = new HashMap<>();
    input2.put("unitId", rawDataLearnRecordModel.getChiefUnitId());
    input2.put("roleType", RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
    input2.put("catId", rawDataLearnRecordModel.getProductCategoryId());
    List<CategorySessionPercentModel> dList =
        dashBoardServiceFacade.getCategorySessionPercentModelByCond(input2);
    if (dList != null && dList.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", dList.get(0).getId());
      dashBoardServiceFacade.editCategorySessionPercentModel(cond, s);
    } else {
      s.setCreateTime(new Timestamp(System.currentTimeMillis()));
      s.setCatId(Integer.parseInt(rawDataLearnRecordModel.getProductCategoryId()));
      s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
      s.setUnitId(rawDataLearnRecordModel.getChiefUnitId());
      s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
      s.setChiefUnitId(rawDataLearnRecordModel.getChiefUnitId());
      dashBoardServiceFacade.addCategorySessionPercent(s);
    }
  }
}
