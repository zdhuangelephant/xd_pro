package com.xiaodou.st.dataclean.service.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dataclean.enums.OrderStatusEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.ScoreDO;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.st.dataclean.service.raw.RawDataCleanService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月30日
 * @description 原始数据清洗Service
 * @version 1.0
 */
@Service("commonService")
public class CommonService {
  @Resource
  DashBoardServiceFacade dashBoardServiceFacade;


  /**
   * 自考办总学生人数
   * 
   * @return
   */
  public Integer getTaughtUnitStudentCount() {
    Map<String, Object> input3 = new HashMap<>();
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    return dashBoardServiceFacade.queryStudentCountByApply(input3);
  }

  /**
   * 主考院校总学生人数
   * 
   * @param chiefUnitId
   * @return
   */
  public Integer unitStundetCount(String chiefUnitId) {
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list != null && list.size() > 0) {
      for (ChiefUnitRelationModel c : list) {
        catIdList.add(c.getCatId());
      }
      Map<String, Object> input3 = new HashMap<>();
      input3.put("catIds", catIdList);
      input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
      return dashBoardServiceFacade.queryStudentCountByApply(input3);
    } else {
      return 0;
    }
  }
  /**
   * 主考院校某个试点单位下的总学生人数
   * 
   * @param chiefUnitId
   * @return
   */
  public Integer unitStundetCount(String chiefUnitId, String pilotUnitId) {
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list != null && list.size() > 0) {
      for (ChiefUnitRelationModel c : list) {
        catIdList.add(c.getCatId());
      }
      Map<String, Object> input3 = new HashMap<>();
      input3.put("catIds", catIdList);
      input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
      input3.put("pilotUnitId", pilotUnitId);
      return dashBoardServiceFacade.queryStudentCountByApply(input3);
    } else {
      return 0;
    }
  }

  /**
   * 试点单位总学生人数
   * 
   * @param pilotUnitId
   * @return
   */
  public Integer getPilotUnitStudentCount(String pilotUnitId) {

    Map<String, Object> input3 = new HashMap<>();
    input3.put("pilotUnitId", pilotUnitId);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    return dashBoardServiceFacade.queryStudentCountByApply(input3);
  }


  /**
   * 某试点单位某专业的学生人数
   * 
   * @param pilotUnitId
   * @param catId
   * @return
   */
  public Integer getStudentCountByCond(Object pilotUnitId, String catId) {
    return getStudentCountByCond(pilotUnitId, Lists.newArrayList(catId));
  }

  /**
   * 某试点单位某专业的学生人数
   * 
   * @param pilotUnitId
   * @param catIds
   * @return
   */
  public Integer getStudentCountByCond(Object pilotUnitId, List<?> catIds) {
    Map<String, Object> input3 = new HashMap<>();
    if (pilotUnitId != null) {
      input3.put("pilotUnitId", pilotUnitId);
    }
    if (catIds != null && catIds.size() > 0) {
      input3.put("catIds", catIds);
    }
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    return dashBoardServiceFacade.queryStudentCountByApply(input3);
  }


  /**
   * 自考办下当期已缴费成功的课程科次
   * 
   * @return
   */
  public Integer getTaughtUnitSubjects() {
    Map<String, Object> input3 = new HashMap<>();
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyList = dashBoardServiceFacade.querySubjectsByCond(input3);
    return !CollectionUtils.isEmpty(applyList) ? applyList.size() : 0;
  }

  /**
   * 主考院校下当期已缴费成功的课程科次
   * 
   * @param chiefUnitId
   * @return
   */
  public Integer unitSubjects(String chiefUnitId) {
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (CollectionUtils.isEmpty(list)) return 0;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    List<ApplyModel> applyList = dashBoardServiceFacade.querySubjectsByCond(input3);
    return !CollectionUtils.isEmpty(applyList) ? applyList.size() : 0;
  }
  /**
   * 主考院校下某试点单位当期已缴费成功的课程科次
   * 
   * @param chiefUnitId
   * @return
   */
  public Integer unitSubjects(String chiefUnitId, String pilotUnitId) {
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (CollectionUtils.isEmpty(list)) return 0;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("pilotUnitId", pilotUnitId);
    List<ApplyModel> applyList = dashBoardServiceFacade.querySubjectsByCond(input3);
    return !CollectionUtils.isEmpty(applyList) ? applyList.size() : 0;
  }


  /**
   * 试点单位下当期已缴费成功的课程科次
   * 
   * @param pilotUnitId
   * @return
   */
  public Integer getPilotUnitSubjects(String pilotUnitId) {
    Map<String, Object> input3 = new HashMap<>();
    input3.put("pilotUnitId", pilotUnitId);
    List<ApplyModel> applyList = dashBoardServiceFacade.querySubjectsByCond(input3);
    return !CollectionUtils.isEmpty(applyList) ? applyList.size() : 0;
  }


  /**
   * 某试点单位某专业下当期已缴费成功的课程科次
   * 
   * @param pilotUnitId
   * @param catId
   * @return
   */
  public Integer getSubjectsByCond(Object pilotUnitId, String catId) {
    return getSubjectsByCond(pilotUnitId, Lists.newArrayList(catId));
  }

  /**
   * 某试点单位某专业下当期已缴费成功的课程科次
   * 
   * @param pilotUnitId
   * @param catIds
   * @return
   */
  public Integer getSubjectsByCond(Object pilotUnitId, List<?> catIds) {
    Map<String, Object> input3 = new HashMap<>();
    if (pilotUnitId != null) {
      input3.put("pilotUnitId", pilotUnitId);
    }
    if (catIds != null && catIds.size() > 0) {
      input3.put("catIds", catIds);
    }
    List<ApplyModel> applyList = dashBoardServiceFacade.querySubjectsByCond(input3);
    return !CollectionUtils.isEmpty(applyList) ? applyList.size() : 0;
  }

  /**
   * 自考办下学习使用科次占比
   * 
   * @return
   */
  public Double getTaughtUnitStuSubjects() {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);

    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input = Maps.newHashMap();
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input);
    if (applyLists.size() == 0) return 0D;
    return (double) learnRecordList.size() / applyLists.size();
  }

  /**
   * 主考院校下学习使用科次占比
   */
  public Double getChiefUnitStuSubjects(String chiefUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("chiefUnitId", chiefUnitId);
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);

    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return 0D;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return 0D;
    return (double) learnRecordList.size() / applyLists.size();
    
  }
  /**
   * 主考院校下某试点单位学习使用科次占比
   */
  public Double getChiefUnitStuSubjects(String chiefUnitId, String pilotUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("chiefUnitId", chiefUnitId);
    cond.put("pilotUnitId", pilotUnitId);
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);
    
    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return 0D;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    input3.put("pilotUnitId", pilotUnitId);
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return 0D;
    return (double) learnRecordList.size() / applyLists.size();
    
  }


  /**
   * 试点单位下学习使用科次占比
   * 
   * @return
   */
  public Double getPilotUnitStuSubjects(String pilotUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("pilotUnitId", pilotUnitId);
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);
    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("pilotUnitId", pilotUnitId);
    input.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input);
    if (applyLists.size() == 0) return 0D;
    return (double) learnRecordList.size() / applyLists.size();
  }


  /**
   * 自考办下及格科次占比(60分及以上及格科次)
   * 
   * @return
   */
  public Double getTaughtUnitAboveGeneralSubjects() {
    List<ScoreDO> aboveGeneralList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0D;
    for (ScoreDO score : resPage.getResult()) {
      if (score.getScore().doubleValue() >= 60) aboveGeneralList.add(score);
    }

    if (aboveGeneralList.size() == 0) return 0D;
    if(getTaughtUnitSubjects().intValue() == 0) return 0D;
    return (double) aboveGeneralList.size() / getTaughtUnitSubjects();
  }

  /**
   * 主考院校下及格科次占比(60分及以上及格科次)
   * 
   * @return
   */
  public Double getChiefUnitAboveGeneralSubjects(String chiefUnitId) {
    List<ScoreDO> aboveGeneralList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0D;
    for (ScoreDO score : resPage.getResult()) {
      if (score.getScore().doubleValue() >= 60) aboveGeneralList.add(score);
    }

    if (aboveGeneralList.size() == 0) return 0D;
    if(unitSubjects(chiefUnitId).intValue() == 0) return 0D;
    return (double) aboveGeneralList.size() / unitSubjects(chiefUnitId);
  }
  /**
   * 主考院校下某试点单位及格科次占比(60分及以上及格科次)
   * 
   * @return
   */
  public Double getChiefUnitAboveGeneralSubjects(String chiefUnitId, String pilotUnitId) {
    List<ScoreDO> aboveGeneralList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0D;
    for (ScoreDO score : resPage.getResult()) {
      if (score.getScore().doubleValue() >= 60) aboveGeneralList.add(score);
    }
    
    if (aboveGeneralList.size() == 0) return 0D;
    if(unitSubjects(chiefUnitId, pilotUnitId).intValue() == 0) return 0D;
    return (double) aboveGeneralList.size() / unitSubjects(chiefUnitId, pilotUnitId);
  }

  /**
   * 试点单位下及格科次占比(60分及以上及格科次)
   * 
   * @return
   */
  public Double getPilotUnitAboveGeneralSubjects(String pilotUnit) {
    List<ScoreDO> aboveGeneralList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnit);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0D;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() >= 60) aboveGeneralList.add(score);
    }

    if (aboveGeneralList.size() == 0) return 0D;
    if(getPilotUnitSubjects(pilotUnit).intValue() == 0) return 0D;
    return (double) aboveGeneralList.size() / getPilotUnitSubjects(pilotUnit);
  }


  /**
   * 自考办下0分科次占比
   * @return
   */
  public Double getTaughtUnitZeroScoreSubjects() {
    if(getTaughtUnitSubjects() == 0D) return 0D;
    return (double) getTaughtUnitPoorSubjects() / getTaughtUnitSubjects();
    
  }


  /**
   * 主考院校下0分科次占比
   * 
   * @return
   */
  public Double getChiefUnitZeroScoreSubjects(String chiefUnitId) {
    if(unitSubjects(chiefUnitId).intValue() == 0) return 0D;
    return (double) getChiefUnitPoorSubjects(chiefUnitId) / unitSubjects(chiefUnitId);
  }


  /**
   * 试点单位下0分科次占比
   * 
   * @return
   */
  public Double getPilotUnitZeroScoreSubjects(String pilotUnit) {
    if(getPilotUnitSubjects(pilotUnit).intValue() == 0) return 0d;
    return (double) getPilotUnitPoorSubjects(pilotUnit) / getPilotUnitSubjects(pilotUnit);
  }


  /**
   * 自考办下100分科次
   * 
   * @return
   */
  public Integer getTaughtUnitFullCreditSubjects() {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() == 100) fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 主考院校下100分科次
   * 
   * @return
   */
  public Integer getChiefUnitFullCreditSubjects(String chiefUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() == 100) fullCreditList.add(score);
    }
    return fullCreditList.size();
  }
  /**
   * 主考院校下某个试点单位的100分科次
   * 
   * @return
   */
  public Integer getChiefUnitFullCreditSubjects(String chiefUnitId, String pilotUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() == 100) fullCreditList.add(score);
    }
    return fullCreditList.size();
  }



  /**
   * 试点单位下100分科次
   * 
   * @return
   */
  public Integer getPilotUnitFullCreditSubjects(String pilotUnit) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnit);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() == 100) fullCreditList.add(score);
    }

    return fullCreditList.size();
  }


  /**
   * 自考办下(100-80]分
   * 
   * @return
   */
  public Integer getTaughtUnitExcellentSubjects() {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 100 && score.getScore().doubleValue() >= 80)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 主考院校下(100-80]分
   * 
   * @return
   */
  public Integer getChiefUnitExcellentSubjects(String chiefUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 100 && score.getScore().doubleValue() >= 80)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }
  /**
   * 主考院校下(100-80]分
   * 
   * @return
   */
  public Integer getChiefUnitExcellentSubjects(String chiefUnitId, String pilotUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 100 && score.getScore().doubleValue() >= 80)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 试点单位下(100-80]分
   * 
   * @return
   */
  public Integer getPilotUnitExcellentSubjects(String pilotUnit) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnit);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 100 && score.getScore().doubleValue() >= 80)
        fullCreditList.add(score);
    }

    return fullCreditList.size();
  }



  /**
   * 自考办下 (80-60]分
   * 
   * @return
   */
  public Integer getTaughtUnitGoodSubjects() {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 80 && score.getScore().doubleValue() >= 60)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 主考院校下 (80-60]分
   * 
   * @return
   */
  public Integer getChiefUnitGoodSubjects(String chiefUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 80 && score.getScore().doubleValue() >= 60)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }
  /**
   * 主考院校下 (80-60]分
   * 
   * @return
   */
  public Integer getChiefUnitGoodSubjects(String chiefUnitId, String pilotUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 80 && score.getScore().doubleValue() >= 60)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 试点单位下 (80-60]分
   * 
   * @return
   */
  public Integer getPilotUnitGoodSubjects(String pilotUnit) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnit);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 80 && score.getScore().doubleValue() >= 60)
        fullCreditList.add(score);
    }

    return fullCreditList.size();
  }



  /**
   * 自考办下 (60-0)分
   * 
   * @return
   */
  public Integer getTaughtUnitGeneralSubjects() {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 60 && score.getScore().doubleValue() > 0)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 主考院校下 (60-0)分
   * 
   * @return
   */
  public Integer getChiefUnitGeneralSubjects(String chiefUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 60 && score.getScore().doubleValue() > 0)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }
  /**
   * 主考院校下某个指点单位的 (60-0)分
   * 
   * @return
   */
  public Integer getChiefUnitGeneralSubjects(String chiefUnitId, String pilotUnitId) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 60 && score.getScore().doubleValue() > 0)
        fullCreditList.add(score);
    }
    return fullCreditList.size();
  }


  /**
   * 试点单位下 (60-0)分
   * 
   * @return
   */
  public Integer getPilotUnitGeneralSubjects(String pilotUnit) {
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnit);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return 0;
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore().doubleValue() < 60 && score.getScore().doubleValue() > 0)
        fullCreditList.add(score);
    }

    return fullCreditList.size();
  }



  /**
   * 自考办下 0分科次 (课程成绩为0分科次，含无任何学习记录提交科次)
   * 
   * @return
   */
  public Integer getTaughtUnitPoorSubjects() {
    HashMap<String, Object> infos = Maps.newHashMap();

    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 1);
    inputs.put("unitId", 1);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return getTaughtUnitSubjects();
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore() == null || score.getScore().doubleValue() == 0) fullCreditList.add(score);
      infos.put(String.format("%s=%s", score.getStudentId(), score.getProductId()), score);
    }

    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input = Maps.newHashMap();
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input);
    if (applyLists.size() == 0) return getTaughtUnitSubjects();
    return (applyLists.size() - infos.size()) + fullCreditList.size();
  }


  /**
   * 主考院校下 0分科次 (课程成绩为0分科次，含无任何学习记录提交科次)
   * 
   * @return
   */
  public Integer getChiefUnitPoorSubjects(String chiefUnitId) {
    if("16".equals(chiefUnitId)) {
      System.out.println("hello");
    }
    HashMap<String, Object> infos = Maps.newHashMap();
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return unitSubjects(chiefUnitId);
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore() == null || score.getScore().doubleValue() == 0) fullCreditList.add(score);
      infos.put(String.format("%s=%s", score.getStudentId(), score.getProductId()), score);
    }

    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return unitSubjects(chiefUnitId);
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return unitSubjects(chiefUnitId);
    return (applyLists.size() - infos.size()) + fullCreditList.size();
  }
  /**
   * 主考院校下 某试点单位的0分科次 (课程成绩为0分科次，含无任何学习记录提交科次)
   * 
   * @return
   */
  public Integer getChiefUnitPoorSubjects(String chiefUnitId, String pilotUnitId) {
    HashMap<String, Object> infos = Maps.newHashMap();
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    // ScoreDO
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 2);
    inputs.put("unitId", chiefUnitId);
    inputs.put("pilotUnitId", pilotUnitId);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return unitSubjects(chiefUnitId, pilotUnitId);
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore() == null || score.getScore().doubleValue() == 0) fullCreditList.add(score);
      infos.put(String.format("%s=%s", score.getStudentId(), score.getProductId()), score);
    }
    
    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return unitSubjects(chiefUnitId, pilotUnitId);
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    input3.put("pilotUnitId", pilotUnitId);
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return unitSubjects(chiefUnitId, pilotUnitId);
    return (applyLists.size() - infos.size()) + fullCreditList.size();
  }


  /**
   * 试点单位下 0分科次 (课程成绩为0分科次，含无任何学习记录提交科次)
   * 
   * @return
   */
  public Integer getPilotUnitPoorSubjects(String pilotUnitId) {
    HashMap<String, Object> infos = Maps.newHashMap();
    List<ScoreDO> fullCreditList = Lists.newArrayList();
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", 3);
    inputs.put("unitId", pilotUnitId);
//    Page<ScoreDO> resPage =
//        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    Page<ScoreDO> resPage =
        dashBoardServiceFacade.listScore0(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null == resPage || resPage.getResult().size() == 0) return getPilotUnitSubjects(pilotUnitId);
    for (ScoreDO score : resPage.getResult()) {
      if(score.getScore() == null) continue;
      if (score.getScore() == null || score.getScore().doubleValue() == 0) fullCreditList.add(score);
      infos.put(String.format("%s=%s", score.getStudentId(), score.getProductId()), score);
    }

    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input2 = Maps.newHashMap();
    input2.put("pilotUnitId", pilotUnitId);
    input2.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input2);
    if (applyLists.size() == 0) return getPilotUnitSubjects(pilotUnitId);
    return (applyLists.size() - infos.size()) + fullCreditList.size();
  }



  /**
   * 自考办下无学习使用科次
   * 
   * @return
   */
  public Integer getTaughtUnitNoneStuSubjects() {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);

    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input = Maps.newHashMap();
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input);
    if (applyLists.size() == 0) return 0;
    Integer res = applyLists.size() - learnRecordList.size();
    return res >= 0 ? res : 0;
    
  }

  /**
   * 主考院校下无学习使用科次
   */
  public Integer getChiefUnitNoneStuSubjects(String chiefUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("chiefUnitId", chiefUnitId);
    
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);

    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return 0;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return 0;
    return applyLists.size() - learnRecordList.size();
    
  }
  
  /**
   * 主考院校下某个试点单位无学习使用科次
   */
  public Integer getChiefUnitNoneStuSubjects(String chiefUnitId, String pilotUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("chiefUnitId", chiefUnitId);
    cond.put("pilotUnitId", pilotUnitId);
    
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);
    
    /* 当期已缴费成功的课程总科次 */
    // 查询主考院校旗下专业
    List<ChiefUnitRelationModel> list =
        dashBoardServiceFacade.getUnitByChief(Integer.valueOf(chiefUnitId));
    List<Integer> catIdList = Lists.newArrayList();
    if (list == null || list.size() == 0) return 0;
    for (ChiefUnitRelationModel c : list) {
      catIdList.add(c.getCatId());
    }
    Map<String, Object> input3 = new HashMap<>();
    input3.put("catIds", catIdList);
    input3.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    input3.put("pilotUnitId", pilotUnitId);
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input3);
    if (applyLists.size() == 0) return 0;
    return applyLists.size() - learnRecordList.size();
    
  }


  /**
   * 试点单位下无学习使用科次
   * 
   * @return
   */
  public Integer getPilotUnitNoneStuSubjects(String pilotUnitId) {
    /* 当期学期内含有学习记录提交过的科次 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("pilotUnitId", pilotUnitId);
    List<RawDataLearnRecordModel> learnRecordList =
        dashBoardServiceFacade.querySubjectsByLearnRecord(cond);
    /* 当期已缴费成功的课程总科次 */
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("pilotUnitId", pilotUnitId);
    input.put("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    List<ApplyModel> applyLists = dashBoardServiceFacade.querySubjectsByCond(input);
    if (applyLists.size() == 0) return 0;
    return applyLists.size() - learnRecordList.size();
  }
}
