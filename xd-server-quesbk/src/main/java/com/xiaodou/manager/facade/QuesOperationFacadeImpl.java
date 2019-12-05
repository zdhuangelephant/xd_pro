package com.xiaodou.manager.facade;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.dao.behavior.UserChapterRecordDao;
import com.xiaodou.dao.behavior.UserCourseStatisticDayDao;
import com.xiaodou.dao.behavior.UserExamRecordDao;
import com.xiaodou.dao.behavior.UserExamTotalDao;
import com.xiaodou.dao.behavior.UserFinalExamRecordDao;
import com.xiaodou.dao.behavior.UserOrderDao;
import com.xiaodou.dao.behavior.UserScorePointRecordDao;
import com.xiaodou.dao.behavior.UserWrongRecordCollectDao;
import com.xiaodou.dao.behavior.UserWrongRecordDao;
import com.xiaodou.dao.product.ChallengeRecordDao;
import com.xiaodou.dao.product.ChallengeRobotDao;
import com.xiaodou.dao.product.CourseProductDao;
import com.xiaodou.dao.product.CourseProductItemDao;
import com.xiaodou.dao.product.FinalExamDao;
import com.xiaodou.dao.product.ProductScorePointRuleDao;
import com.xiaodou.dao.product.QuesbkAudioLogDao;
import com.xiaodou.dao.product.QuesbkExamPaperDao;
import com.xiaodou.dao.product.QuesbkExamRuleDao;
import com.xiaodou.dao.product.QuesbkQuesDao;
import com.xiaodou.dao.product.QuesbkQuesStatisticsDao;
import com.xiaodou.dao.product.QuesbkQuesTypeDao;
import com.xiaodou.dao.product.RaiseWrongQuesDao;
import com.xiaodou.dao.product.RedBounsDao;
import com.xiaodou.dao.product.RegionModelDao;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserCourseStatisticDayModel;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamRecordDetail;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserExamTotal.ChapterScore;
import com.xiaodou.domain.behavior.UserFinalExamRecord;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.domain.product.ProductScorePointRule;
import com.xiaodou.domain.product.ProductScorePointRule.RuleInfo;
import com.xiaodou.domain.product.QuesbkAudioLog;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.domain.product.QuesbkQuesStatistics;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.domain.product.RaiseWrongQues;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RegionModel;
import com.xiaodou.engine.rule.factory.RuleFactory;
import com.xiaodou.engine.rule.iterface.IRuleEntity;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.manager.cache.QuesbkCache;
import com.xiaodou.mission.engine.enums.RedBonusType;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.UpdateParam;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor;
import com.xiaodou.util.StaticInfoProp;
import com.xiaodou.vo.mq.UserExamTotalEvent;
import com.xiaodou.vo.mq.UserExamTotalEvent.TransferExamTotal;
import com.xiaodou.vo.request.ExamDetailPojo;
import com.xiaodou.vo.request.ExamDetailPojo_v1_3_8;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @name @see com.xiaodou.service.facade.QuesOperationFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库数据源上层Facade实现
 * @version 1.0
 */
@Service("quesOperationFacade")
public class QuesOperationFacadeImpl implements QuesOperationFacade {
  @Resource
  QuesbkCache quesbkCache;

  @Resource
  UserExamRecordDao userExamRecordDao;

  @Resource
  UserWrongRecordDao userWrongRecordDao;

  @Resource
  UserWrongRecordCollectDao userWrongRecordCollectDao;

  @Resource
  UserExamTotalDao userExamTotalDao;

  @Resource
  QuesbkExamRuleDao quesbkExamRuleDao;

  @Resource
  QuesbkExamPaperDao quesbkExamPaperDao;

  @Resource
  QuesbkAudioLogDao quesbkAudioLogDao;

  @Resource
  QuesbkQuesDao quesbkQuesDao;

  @Resource
  QuesbkQuesTypeDao quesbkQuesTypeDao;

  @Resource
  SummerTaskExecutor quesbkServiceTaskExecutor;

  @Resource(name = "courseProductDao")
  CourseProductDao courseProductDao;

  @Resource
  CourseProductItemDao courseProductItemDao;

  @Resource
  QuesbkQuesStatisticsDao qsuesbkQuesStatisticsDao;

  @Resource
  ChallengeRecordDao challengeRecordDao;

  @Resource
  RaiseWrongQuesDao raiseWrongQuesDao;

  @Resource
  UserChapterRecordDao userChapterRecordDao;

  @Resource
  ChallengeRobotDao challengeRobotDao;

  @Resource
  UserCourseStatisticDayDao userCourseStatisticDayDao;

  @Resource
  RedBounsDao redBounsDao;

  @Resource
  UserFinalExamRecordDao userFinalExamRecordDao;

  @Resource
  FinalExamDao finalExamDao;

  @Resource
  UserOrderDao userOrderDao;

  @Resource
  UserScorePointRecordDao userScorePointRecordDao;

  @Resource
  ProductScorePointRuleDao productScorePointRuleDao;

  @Resource
  RegionModelDao regionModelDao;

  @Override
  public UserExamTotal queryExamTotal(String uid, String courseId) {
    if (StringUtils.isOrBlank(uid, courseId)) return null;
    return userExamTotalDao.selectByUidAndSubjectId(uid, courseId);
  }

  @Override
  public Integer countExamTotal(String courseId) {
    return userExamTotalDao.selectAllUserCountBySubjectId(courseId);
  }

  @Override
  public Boolean updateExamTotal(UserExamTotal myExamTotal) {
    return userExamTotalDao.updateByPrimaryKeySelective(myExamTotal) == 1;
  }

  @Override
  public Boolean insertExamTotal(UserExamTotal myExamTotal) {
    return userExamTotalDao.insertSelective(myExamTotal) == 1;
  }

  @Override
  public Boolean updateOrAddEntity(UserExamTotal value, Map<String, Object> column) {
    return userExamTotalDao.updateOrAddEntity(value, column) == 1;
  }

  @Override
  public List<QuesbkExamPaper> queryExamPaperList(String courseId, String examType) {
    return quesbkExamPaperDao.selectBySubjectIdAndExamType(courseId, examType);
  }

  @Override
  public QuesbkExamPaper queryExamPaper(String paperId) {
    return quesbkExamPaperDao.selectByPrimaryKey(paperId);
  }

  @Override
  public List<QuesbkQues> queryQuesList(List<String> quesIds, String userId, String courseId) {
    Map<String, Object> paraMap = Maps.newHashMap();
    paraMap.put("list", quesIds);
    paraMap.put("userId", userId);
    paraMap.put("courseId", courseId);
    return quesbkQuesDao.selectByPrimaryKeyList(paraMap);
  }

  @Override
  public QuesbkQues queryQues(String quesId, String courseId) {
    return quesbkQuesDao.selectByPrimaryKey(quesId, courseId);
  }


  @Override
  public Boolean updateContinueExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordDao.updateByPrimaryKey(userExamRecord) == 1;
  }

  @Override
  public Boolean updateResetExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordDao.updateByPrimaryKeySelective(userExamRecord) == 1;
  }

  @Override
  public Boolean insertExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordDao.insert(userExamRecord) == 1;
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String uid, String courseId) {
    return userExamRecordDao.selectByUidAndSubjectId(uid, courseId);
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String uid, String courseId, String examTypeId) {
    return userExamRecordDao.selectByUidSubjectIdAndExamTypeId(uid, courseId, examTypeId);
  }

  @Override
  public List<UserExamRecord> queryNotInExamRecordList(String uid, String courseId,
      List<String> examTypeId, List<String> paperIdList) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", uid);
    params.put("courseId", courseId);
    params.put("examTypeIdList", examTypeId);
    params.put("paperIdList", paperIdList);
    return userExamRecordDao.selectByNotUidSubjectIdAndExamTypeId(params);
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String paperId) {
    return userExamRecordDao.selectByPaperId(paperId);
  }

  @Override
  public Integer queryTodayQuesCount(String uid, List<String> courseIdList) {
    Integer todayExamQuesCount = 0;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("uid", uid);
    cond.put("courseIdList", courseIdList);
    List<String> examRecordDetailList = quesbkExamPaperDao.queryTodayExamQuesList(cond);
    if (null == examRecordDetailList || examRecordDetailList.size() == 0) return null;
    for (String examRecordDetail : examRecordDetailList) {
      if (StringUtils.isJsonBlank(examRecordDetail)) {
        continue;
      }
      UserExamRecordDetail recordDetail =
          FastJsonUtil.fromJson(examRecordDetail, UserExamRecordDetail.class);
      if (null == recordDetail || null == recordDetail.getFinishCount()) {
        continue;
      }
      todayExamQuesCount += recordDetail.getQuesCount();
    }
    return todayExamQuesCount;
  }

  @Override
  public List<String> queryTotalQuesIdList(String uid, List<String> courseIdList) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("uid", uid);
    cond.put("courseIdList", courseIdList);
    List<String> todayQuesIdList = quesbkExamPaperDao.queryTotalExamPaperList(cond);
    if (null == todayQuesIdList || todayQuesIdList.size() == 0) return null;
    List<String> quesIdList = Lists.newArrayList();
    for (String todayQuesId : todayQuesIdList) {
      if (StringUtils.isJsonBlank(todayQuesId)) continue;
      quesIdList.addAll(FastJsonUtil.fromJsons(todayQuesId, new TypeReference<List<String>>() {}));
    }
    return quesIdList;
  }

  @Override
  public Page<UserWrongRecord> queryWrongRecordList(Map<String, Object> cond,
      Page<UserWrongRecord> page) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    return userWrongRecordDao.findListByCond(input, page);
  }

  @Override
  public Integer countTotalQuesRank(Integer count, String uid, String courseId) {
    if (StringUtils.isOrBlank(uid, courseId) || null == count) return -1;
    return userExamTotalDao.queryTotalRankByCond(count.toString(), uid, courseId);
  }

  @Override
  public Integer countRightQuesRank(Integer count, String uid, String courseId) {
    if (StringUtils.isOrBlank(uid, courseId) || null == count) return -1;
    return userExamTotalDao.queryRightRankByCond(count.toString(), uid, courseId);
  }

  @Override
  public Page<UserWrongRecord> queryAbstractWrongRecordList(Map<String, Object> cond,
      Page<UserWrongRecord> page) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    return userWrongRecordDao.findEntityByCond(input, page);
  }

  @Override
  public Integer countWrongRecord(Map<String, Object> cond) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    return userWrongRecordDao.queryCountByCond(input);
  }

  @Override
  public List<UserWrongRecordCollect> queryWrongRecordCollectList(String uid, String courseId) {
    // TODO
    // 1、user
    // 2、product
    // 3、untion
    return userWrongRecordCollectDao.selectByUserIdAndCourseId(uid, courseId);
  }

  @Override
  public UserWrongRecordCollect queryWrongRecordCollect(String uid, String courseId, String itemId) {
    return userWrongRecordCollectDao.selectByUserIdAndCourseIdItemId(uid, courseId, itemId);
  }

  @Override
  public int updateWrongRecordCollect(UserWrongRecordCollect userWrongRecordCollect) {
    return userWrongRecordCollectDao.updateByUSISelective(userWrongRecordCollect);
  }

  @Override
  public void insertWrongRecordCollect(UserWrongRecordCollect entity) {
    userWrongRecordCollectDao.insertEntity(entity);
  }

  @Override
  public List<QuesbkExamRule> queryExamRuleList(String productId, String examTypeId) {
    return quesbkExamRuleDao.selectByProductIdAndExamType(productId, examTypeId);
  }

  @Override
  public UserExamRecord queryExamRecord(String paperId, String userId) {
    return userExamRecordDao.selectByUidAndPaperId(paperId, userId);
  }

  @Override
  public List<UserExamRecord> selectExamCostByExamCost(Map<String, Object> params) {
    return userExamRecordDao.selectExamCostByExamCost(params);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List<Object> queryExamQuestionIdList_v1_3_8(ExamDetailPojo_v1_3_8 pojo,
      QuesbkExamPaper paper) throws Exception {
    // 组卷规则列表
    List<QuesbkExamRule> examRuleList = Lists.newArrayList();
    // 判断练习类型是否存在默认组卷规则，若不存在则从数据库中查询获取该练习类型的组卷规则
    if (StaticInfoProp.isNotDefaultRule(pojo.getExamType()))
      examRuleList =
          quesbkExamRuleDao.selectByProductIdAndExamType(pojo.getCourseId(), pojo.getExamType());
    // 获取出题类型列表
    List<QuesbkQuesType> quesTypeList = queryQuesTypeList();
    // 获取该练习类型的出题规则实体
    IRuleEntity rule = RuleFactory.getRule(pojo.getExamType(), examRuleList, paper);
    if (null == rule) return null;
    RuleEntityParam param =
        new RuleEntityParam(this, quesbkServiceTaskExecutor, quesTypeList, pojo.getUid(),
            pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo.getPriorQues(),
            pojo.getLastQuesId());
    // 2017/11/13_ADD_BY zhaodan 将初始化规则列表从组卷逻辑中抽离出来,方便1.3.8版本以后出题逻辑整体变更为命题蓝图出圈后对老版本的兼容
    rule.initRule_v1_3_8(param);
    // 根据出题规则组卷
    return rule.getQuestions(new RuleEntityParam(this, quesbkServiceTaskExecutor, quesTypeList,
        pojo.getUid(), pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo
            .getPriorQues(), pojo.getLastQuesId()));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List<Object> queryExamQuestionIdList(ExamDetailPojo pojo, List<QuesbkExamRule> ruleList,
      QuesbkExamPaper paper) throws Exception {
    // 获取出题类型列表
    List<QuesbkQuesType> quesTypeList = queryQuesTypeList();
    // 获取该练习类型的出题规则实体
    IRuleEntity rule = RuleFactory.getRule(pojo.getExamType(), ruleList, paper);
    if (null == rule) return null;
    RuleEntityParam param =
        new RuleEntityParam(this, quesbkServiceTaskExecutor, quesTypeList, pojo.getUid(),
            pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo.getPriorQues(),
            pojo.getLastQuesId());
    // 2017/11/13_ADD_BY zhaodan 将初始化规则列表从组卷逻辑中抽离出来,方便1.3.8版本以后出题逻辑整体变更为命题蓝图出圈后对老版本的兼容
    rule.initRule(param);
    // 根据出题规则组卷
    return rule.getQuestions(param);
  }

  @Override
  public List<QuesbkQuesType> queryQuesTypeList() {
    List<QuesbkQuesType> quesTypeLst = quesbkCache.getQuesType();
    if (null == quesTypeLst) {
      return quesbkQuesTypeDao.selectQuesType();
    }
    return quesTypeLst;
  }

  // @Override
  // public List<QuesbkQues> queryQuesList(Rule rule) {
  // return quesbkQuesDao.selectByRule(rule);
  // }

  @Override
  public List<Long> queryQuesIdList(Rule rule) {
    return quesbkQuesDao.selectQuesIdByRule(rule);
  }

  @Override
  public List<QuesbkQues> queryQuesListByIdList(String userId, String courseId,
      List<Long> quesIdList) {
    Map<String, Object> paramMap = Maps.newHashMap();
    paramMap.put("userId", userId);
    paramMap.put("courseId", courseId);
    paramMap.put("idList", quesIdList);
    List<QuesbkQues> quesList = quesbkQuesDao.selectByIdList(paramMap);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    cond.put("questionIdList", quesIdList);
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    Page<UserWrongRecord> myWrongQuesPage = userWrongRecordDao.findEntityByCond(input, null);
    if (null != quesList && !quesList.isEmpty() && null != myWrongQuesPage
        && null != myWrongQuesPage.getResult() && !myWrongQuesPage.getResult().isEmpty()) {
      Map<Long, UserWrongRecord> myWrongQuesMap = Maps.newHashMap();
      for (UserWrongRecord myWrongQues : myWrongQuesPage.getResult()) {
        myWrongQuesMap.put(myWrongQues.getQuestionId(), myWrongQues);
      }
      for (QuesbkQues ques : quesList) {
        if (null == ques) {
          continue;
        }
        UserWrongRecord myWrongQues = myWrongQuesMap.get(ques.getId());
        if (null == myWrongQues) {
          continue;
        }
        if (null != ques.getStatistic()) {
          ques.getStatistic().setMyExamTimes(myWrongQues.getTotalTimes());
          ques.getStatistic().setMyWrongTimes(myWrongQues.getWrongTimes());
        }
        int myRightTimes =
            (null != myWrongQues.getTotalTimes() && null != myWrongQues.getWrongTimes() && myWrongQues
                .getTotalTimes() > myWrongQues.getWrongTimes()) ? myWrongQues.getTotalTimes()
                - myWrongQues.getWrongTimes() : 0;
        ques.setMyRightTimes(myRightTimes);
        ques.setWrongStatus(myWrongQues.getWrongStatus());
        ques.setMyAnswerIds(myWrongQues.getWrongAnswer());
      }
    }
    return quesList;
  }

  @Override
  public List<QuesbkQues> queryAbstractQuesList(Rule rule) {
    return quesbkQuesDao.selectAbstractByRule(rule);
  }

  @Override
  public List<CourseProduct> queryProductList(String module, String typeCode) {
    List<CourseProduct> productList = quesbkCache.getProductList(module, typeCode);
    if (null == productList) {
      return courseProductDao.selectByModuleAndTypeCode(module, typeCode);
    }
    return productList;
  }

  @Override
  public List<String> queryTypeCodeList(String module, String courseId) {
    return courseProductDao.selectTypeCodeByModuleAndCourseId(module, courseId);
  }

  @Override
  public List<CourseProductItem> queryChapterItemList(String productId) {
    List<CourseProductItem> productItemList = quesbkCache.getChapterItemList(productId);
    if (null == productItemList) {
      return courseProductItemDao.selectByProductId(productId);
    }
    return productItemList;
  }

  @Override
  public List<CourseProductItem> queryChapterList(String courseId) {
    List<CourseProductItem> productItemList = quesbkCache.getChapterList(courseId);
    if (null == productItemList) {
      return courseProductItemDao.selectChapterByProductId(courseId);
    }
    return productItemList;
  }

  @Override
  public List<CourseProductItem> queryItemList(String courseId, String chapterId) {
    List<CourseProductItem> productItemList = quesbkCache.getItemList(courseId, chapterId);
    if (null == productItemList) {
      return courseProductItemDao.selectItemByProductIdAndChapterId(courseId, chapterId);
    }
    return productItemList;
  }

  @Override
  public CourseProductItem queryItem(String courseId, String chapterId, String itemId) {
    return courseProductItemDao.selectItemByProductIdAndChapterIdAndItemId(courseId, chapterId,
        itemId);
  }

  @Override
  public CourseProductItem queryChapter(String courseId, String chapterId) {
    return courseProductItemDao.selectChapterByProductIdAndChapterId(courseId, chapterId);
  }

  @Override
  public List<CourseProductItem> queryChapterItemList(String courseId, String userId) {
    return courseProductItemDao.selectByProductIdAndUserId(courseId, userId);

    // step1 获取所有的用户错题记录
    /*
     * IQueryParam userWrongRecordParam = new QueryParam(); if (StringUtils.isNotBlank(userId)) {
     * userWrongRecordParam.addInput("userId", userId); } userWrongRecordParam.addOutput("userId",
     * 1); userWrongRecordParam.addOutput("courseId", 1);
     * userWrongRecordParam.addOutput("chapterId", 1); userWrongRecordParam.addOutput("questionId",
     * 1); userWrongRecordParam.addOutput("rightTimes", 1); Page<UserWrongRecord>
     * userWrongRecordPage = userWrongRecordDao.findEntityListByCond(userWrongRecordParam, null);
     * 
     * 
     * 
     * // step 2 调用SQL片段 List<CourseProductItem> itemLists =
     * courseProductItemDao.selectByProductIdAndUserId(courseId, userId);
     * 
     * ArrayList<ProductUserWrongRecordVO> voLists = Lists.newArrayList();
     * 
     * // step3 JOIN链接 if (!CollectionUtils.isEmpty(itemLists)) { for (CourseProductItem
     * courseProductItem : itemLists) { if (null != userWrongRecordPage &&
     * !CollectionUtils.isEmpty(userWrongRecordPage.getResult())) { for (UserWrongRecord
     * userWrongRecord : userWrongRecordPage.getResult()) { if (courseProductItem.getId() ==
     * userWrongRecord.getChapterId() && courseProductItem.getProductId() ==
     * userWrongRecord.getCourseId()) { ProductUserWrongRecordVO vo = new
     * ProductUserWrongRecordVO(); vo.setCourseProductItem(courseProductItem);
     * vo.setUserWrongRecord(userWrongRecord); voLists.add(vo); } } } } }
     * 
     * // step4 分组 if (!CollectionUtils.isEmpty(voLists)) { HashMap<String, UserWrongRecord> group =
     * Maps.newHashMap(); for (ProductUserWrongRecordVO vo : voLists) { CourseProductItem item =
     * vo.getCourseProductItem(); String mapKey = item.getId() + QuesBaseConstant.QUES_TYPE_SPLIT +
     * item.getParentId() + QuesBaseConstant.QUES_TYPE_SPLIT + item.getProductId() +
     * QuesBaseConstant.QUES_TYPE_SPLIT + item.getResourceType() + QuesBaseConstant.QUES_TYPE_SPLIT
     * + item.getName() + QuesBaseConstant.QUES_TYPE_SPLIT + item.getImportanceLevel() +
     * QuesBaseConstant.QUES_TYPE_SPLIT + item.getQuesNum(); if (!group.containsKey(mapKey)) {
     * group.put(mapKey, vo.getUserWrongRecord()); } }
     * 
     * // 求分组以后的Count值 HashMap<String, Integer> counts = Maps.newHashMap(); if
     * (!CollectionUtils.isEmpty(group)) { for (Map.Entry<String, UserWrongRecord> entry :
     * group.entrySet()) { if (entry.getValue().getQuestionId() != null) { if
     * (!counts.containsKey(entry.getKey())) { counts.put(entry.getKey(), 1); } else {
     * counts.put(entry.getKey(), counts.get(entry.getKey()) + 1); } } } }
     * 
     * // 求分组以后的Sum值 HashMap<String, Integer> sums = Maps.newHashMap(); if
     * (!CollectionUtils.isEmpty(group)) { for (Map.Entry<String, UserWrongRecord> entry :
     * group.entrySet()) { if (entry.getValue().getRightTimes() > 0) { if
     * (!sums.containsKey(entry.getKey())) { sums.put(entry.getKey(), 1); } else {
     * sums.put(entry.getKey(), sums.get(entry.getKey()) + 1); } } } }
     */
  }

  @Override
  public CourseProduct queryProduct(String productId) {
    CourseProduct product = quesbkCache.getProductById(productId);
    if (null == product) {
      return courseProductDao.selectById(productId);
    }
    return product;
  }

  @Override
  public void updateWrongTimes(UserWrongRecord oldRecord) {
    userWrongRecordDao.updateWrongTimes(oldRecord);
  }

  @Override
  public void updateRightTimes(UserWrongRecord oldRecord) {
    userWrongRecordDao.updateRightTimes(oldRecord);
  }

  @Override
  public void insertWrongRecord(UserWrongRecord wrongRecord) {
    userWrongRecordDao.addEntity(wrongRecord);
  }

  @Override
  public List<QuesbkQues> queryAbstractQuesList(List<String> quesIds, String courseId) {
    Map<String, Object> paraMap = Maps.newHashMap();
    paraMap.put("list", quesIds);
    paraMap.put("courseId", courseId);
    return quesbkQuesDao.selectAbstractByPrimaryKeyList(paraMap);
  }

  @Override
  public QuesbkQuesStatistics queryQuesStatistic(String quesId, String courseId) {
    return qsuesbkQuesStatisticsDao.selectByQuesId(quesId, courseId);
  }

  @Override
  public void insertQuesStatistic(QuesbkQuesStatistics statistic) {
    qsuesbkQuesStatisticsDao.insertSelective(statistic);
  }

  @Override
  public void updateQuesStatistic(QuesbkQuesStatistics statistic) {
    qsuesbkQuesStatisticsDao.updateByExam(statistic);
  }

  @Override
  public void insertExamPaper(QuesbkExamPaper paper) {
    quesbkExamPaperDao.insert(paper);
  }

  @Override
  public Boolean insertChallengeRecord(ChallengeRecord record) {
    return 1 == challengeRecordDao.insertSelective(record);
  }

  @Override
  public Boolean updateChallengeRecord(ChallengeRecord record) {
    return 1 == challengeRecordDao.updateByPrimaryKeySelective(record);
  }

  @Override
  public ChallengeRecord queryChallengeRecord(String id) {
    return challengeRecordDao.selectByPrimaryKey(id);
  }

  @Override
  public List<ChallengeRecord> queryChallengeRecord(Map<String, Object> cond) {
    return challengeRecordDao.selectByCond(cond);
  }

  @Override
  public Boolean insertRaiseWrongQues(RaiseWrongQues entity) {
    return null != raiseWrongQuesDao.addEntity(entity);
  }

  @Override
  public void changeWrongQues(UserWrongRecord record) {

    // 更新 wrong_record
    userWrongRecordDao.updateEntityByIdAndUid(record);

    // 更新 wrong_record_collect
    // 1、查询
    Map<String, Object> _cond = new HashMap<String, Object>();
    _cond.put("userId", record.getUserId());
    _cond.put("courseId", record.getCourseId());
    _cond.put("chapterId", record.getChapterId());
    _cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    Page<UserWrongRecord> pageList = queryWrongRecordList(_cond, null);
    if (null == pageList) return;
    List<UserWrongRecord> userWrongRecordList = pageList.getResult();
    if (null == userWrongRecordList || userWrongRecordList.size() < 1) return;
    int questionNumber = 0;// 问题数
    int uncatchQuesCount = 0;// 未掌握问题数量
    int catchingQuesCount = 0;// 待强化问题数量
    int catchedQuesCount = 0;// 已消灭问题数量
    // 错题状态 1 未掌握 2 待强化 3 已消灭 4 已移除(默认)
    for (UserWrongRecord userWrongRecord : userWrongRecordList) {
      if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_REMOVED
          .equals(userWrongRecord.getWrongStatus())) continue;
      ++questionNumber;
      switch (userWrongRecord.getWrongStatus()) {
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL:
          ++uncatchQuesCount;
          break;
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_NEEDEXAM:
          ++catchingQuesCount;
          break;
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_RESOLVED:
          ++catchedQuesCount;
          break;
        default:
          break;
      }
    }
    // 2、修改
    UserWrongRecordCollect userWrongRecordCollect =
        queryWrongRecordCollect(record.getUserId(), record.getCourseId().toString(), record
            .getChapterId().toString());
    if (null == userWrongRecordCollect) {
      UserWrongRecordCollect recordCollect = new UserWrongRecordCollect();
      recordCollect.setUserId(record.getUserId());
      recordCollect.setCourseId(record.getCourseId());
      recordCollect.setChapterId(record.getChapterId());
      recordCollect.setQuestionNumber(questionNumber);
      recordCollect.setUncatchQuesCount(uncatchQuesCount);
      recordCollect.setCatchingQuesCount(catchingQuesCount);
      recordCollect.setCatchedQuesCount(catchedQuesCount);
      insertWrongRecordCollect(recordCollect);
    }
    userWrongRecordCollect.setChapterId(record.getChapterId());
    userWrongRecordCollect.setCourseId(record.getCourseId());
    userWrongRecordCollect.setUserId(record.getUserId());
    userWrongRecordCollect.setQuestionNumber(questionNumber);
    userWrongRecordCollect.setUncatchQuesCount(uncatchQuesCount);
    userWrongRecordCollect.setCatchingQuesCount(catchingQuesCount);
    userWrongRecordCollect.setCatchedQuesCount(catchedQuesCount);
    updateWrongRecordCollect(userWrongRecordCollect);
  }

  /*
   * @see com.xiaodou.manager.facade.QuesOperationFacade#queryBuyCourseByCond(java.util.Map) 更新时间:
   * 2018年1月16日 下午1:21:52
   * 
   * @author zdhuang
   * 
   * @version 题库SQL优化(阶段二)
   */
  @Override
  public List<CourseProduct> queryBuyCourseByCond(Map<String, Object> params) {
    return courseProductDao.selectBuyProductByCond(params);
  }

  @Override
  public List<CourseProduct> selectBuyProductByCond0(Map<String, Object> params) {
    return courseProductDao.selectBuyProductByCond0(params);
  }

  @Override
  public Boolean insertUserChapterRecord(UserChapterRecord record) {
    return 1 == userChapterRecordDao.insertSelective(record);
  }

  @Override
  public Boolean updateUserChapterRecord(UserChapterRecord record) {
    return 1 == userChapterRecordDao.updateByPrimaryKey(record);
  }

  @Override
  public UserChapterRecord queryUserChapterRecord(String id) {
    return userChapterRecordDao.selectByPrimaryKey(id);
  }

  @Override
  public List<UserExamTotal> queryExamTotal(Map<String, Object> cond) {
    return userExamTotalDao.queryByCond(cond);
  }

  @Override
  public List<UserChapterRecord> queryUserChapterRecord(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return userChapterRecordDao.selectByCond(cond);
  }

  @Override
  public Page<ChallengeRobot> queryChallengeRobot(Map<String, Object> cond,
      Page<ChallengeRobot> page) {
    return challengeRobotDao.findListByCond(cond, page);
  }

  private void buildUserScorePointRecord(List<RuleInfo> ruleInfoList,
      List<UserScorePointRecord> recordList) {
    Map<Short, UserScorePointRecord> recordMap = Maps.newHashMap();
    for (UserScorePointRecord record : recordList) {
      recordMap.put(record.getRuleType(), record);
    }
    for (RuleInfo ruleInfo : ruleInfoList) {
      UserScorePointRecord record = recordMap.get(ruleInfo.getCode());
      if (null == record) {
        record = new UserScorePointRecord();
        record.setRuleType(ruleInfo.getCode());
        recordList.add(record);
      }
      record.setRuleInfo(ruleInfo);
    }
    Collections.sort(recordList, new Comparator<UserScorePointRecord>() {
      @Override
      public int compare(UserScorePointRecord o1, UserScorePointRecord o2) {
        if (null == o1 || null == o1.getRuleInfo() || null == o1.getRuleInfo().getOrder()
            || o1.getRuleInfo().getOrder() == -1) {
          return 1;
        } else if (null == o2 || null == o2.getRuleInfo() || null == o2.getRuleInfo().getOrder()
            || o2.getRuleInfo().getOrder() == -1) {
          return -1;
        } else {
          return o1.getRuleInfo().getOrder() - o2.getRuleInfo().getOrder();
        }
      }
    });
  }

  private boolean fetchRuleTypeComplete(UserScorePointRecord record) {
    if (record.getRuleInfo().getWeight() <= 0) {
      return true;
    }
    if (ProductConstants.RULE_TYPE_ITEM_PRACTICE.equals(record.getRuleType())
        || ProductConstants.RULE_TYPE_CHAPTER_PRACTICE.equals(record.getRuleType())
        || ProductConstants.RULE_TYPE_FINAL_EXAM.equals(record.getRuleType())) {
      return record.getCompletePercent() == 1d;
    }
    return true;
  }

  @Override
  public void updateUserScoreNoEvent(String module, Long courseId, String uid, String... typeCode) {
    updateUserScore(false, module, courseId, uid, typeCode);
  }

  @Override
  public void updateUserScoreWithEvent(String module, Long courseId, String uid, String... typeCode) {
    updateUserScore(true, module, courseId, uid, typeCode);
  }

  private void updateUserScore(Boolean missionEvent, String module, Long courseId, String uid,
      String... typeCode) {
    Boolean buildChapter = false, buildItem = false, courseComplete = true;
    UserExamTotal oldExamTotal = queryExamTotal(uid, courseId.toString());
    if (null == oldExamTotal) return;
    UserExamTotal examTotal = new UserExamTotal();
    examTotal.setId(oldExamTotal.getId());
    examTotal.setUserId(oldExamTotal.getUserId());
    examTotal.setCourseId(oldExamTotal.getCourseId());
    CourseProduct productModel = queryProduct(courseId.toString());
    if (null == productModel || StringUtils.isBlank(productModel.getRuleId())) {
      return;
    }
    ProductScorePointRule rule = queryProductScorePointRuleById(productModel.getRuleId());
    if (null == rule || StringUtils.isJsonBlank(rule.getRuleDetail())) {
      return;
    }
    List<RuleInfo> ruleInfoList =
        FastJsonUtil.fromJsons(rule.getRuleDetail(), new TypeReference<List<RuleInfo>>() {});
    IQueryParam param = new QueryParam();
    param.addInput("userId", uid);
    param.addInput("productId", courseId);
    param.addOutputs(UserScorePointRecord.class);
    Page<UserScorePointRecord> userScorePointRecordPage = queryUserScorePointRecord(param);
    buildUserScorePointRecord(ruleInfoList, userScorePointRecordPage.getResult());
    Double score = 0d;
    for (UserScorePointRecord record : userScorePointRecordPage.getResult()) {
      if (null == record) {
        continue;
      }
      if (null != record.getRuleInfo() && record.getRuleInfo().getWeight() > 0) {
        if (ProductConstants.RULE_TYPE_ITEM_PRACTICE.equals(record.getRuleType())) {
          // buildItem = true;
        } else if (ProductConstants.RULE_TYPE_CHAPTER_PRACTICE.equals(record.getRuleType())) {
          buildChapter = true;
        }
        score += record.getScore() * record.getRuleInfo().getWeight();
        // 计算课程完成度
        courseComplete &= fetchRuleTypeComplete(record);
      }
    }
    if (score >= 100) {
      score = 100d;
    }
    examTotal.setScore(score);
    oldExamTotal.setScore(score);
    this.updateUserDailyScoreStatistic(uid, module, courseId, examTotal.getScore());
    List<ChapterScore> chapterList = fetchDisplayChapter(examTotal, buildChapter, buildItem);
    if (courseComplete && missionEvent) {
      TollgateEvent event = EventBuilder.buildTollgateEvent();
      event.setUserId(examTotal.getUserId());
      event.setModule(module);
      event.setMajorId(QuesBaseConstant.COMMON_MAJOR_ID);
      event.setCourseId(courseId.toString());
      event.setTollgateId(courseId.toString());
      event.setCount(0);
      event.setCredit(0);
      event.setScore(score);
      event.setStarLevel(score >= 100 ? 3 : score >= 80 ? 2 : score >= 60 ? 1 : 0);
      event.send();
    }
    Collections.sort(chapterList, new Comparator<ChapterScore>() {
      @Override
      public int compare(ChapterScore o1, ChapterScore o2) {
        if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
          return 1;
        else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
          return -1;
        else
          return (int) (o1.getListOrder() - o2.getListOrder());
      }
    });
    examTotal.setChapterScore(FastJsonUtil.toJson(chapterList));
    oldExamTotal.setChapterScore(FastJsonUtil.toJson(chapterList));
    updateExamTotal(examTotal);
    // 添加洗数据事件
    oldExamTotal.setModule(module);
    if (null == typeCode || typeCode.length == 0) {
      return;
    }
    // 期末分
    List<FinalExamModel> finalList = this.selectFinalExamByCond(courseId.toString(), uid);
    for (String typecode : typeCode) {
      UserExamTotalEvent dataCleanEvent = new UserExamTotalEvent();
      dataCleanEvent.setModule(module);
      dataCleanEvent.setDataModel(new TransferExamTotal(typecode, oldExamTotal,
          userScorePointRecordPage.getResult(), finalList));
      dataCleanEvent.send();
    }
  }

  private Map<Long, UserChapterRecord> buildUserChapterRecordMap(
      List<UserChapterRecord> itemRecordList) {
    Map<Long, UserChapterRecord> userChapterRecordMap = Maps.newHashMap();
    if (null != itemRecordList && itemRecordList.size() > 0) {
      for (UserChapterRecord record : itemRecordList) {
        userChapterRecordMap.put(record.getItemId(), record);
      }
    }
    return userChapterRecordMap;
  }

  private List<ChapterScore> fetchDisplayChapter(UserExamTotal examTotal, boolean buildChapter,
      boolean buildItem) {
    List<ChapterScore> chapterList = Lists.newArrayList();
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", examTotal.getUserId());
    cond.put("courseId", examTotal.getCourseId());
    List<UserChapterRecord> itemRecordList = queryUserChapterRecord(cond);
    Map<Long, UserChapterRecord> itemRecordMap = buildUserChapterRecordMap(itemRecordList);
    List<CourseProductItem> itemList = queryChapterItemList(examTotal.getCourseId().toString());
    if (null != itemList && itemList.size() > 0) {
      Collections.sort(itemList, new Comparator<CourseProductItem>() {
        @Override
        public int compare(CourseProductItem o1, CourseProductItem o2) {
          if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
            return 1;
          else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
            return -1;
          else
            return o1.getListOrder().intValue() - o2.getListOrder().intValue();
        }
      });
      for (CourseProductItem item : itemList) {
        if (null == item || null == item.getParentId()) continue;
        if (item.getParentId() > 0 && !buildItem) continue;
        if (item.getParentId() == 0 && !buildChapter) continue;
        ChapterScore chapterScore = new ChapterScore(item);
        UserChapterRecord record = itemRecordMap.get(item.getId());
        if (null != record && null != record.getScore()) {
          chapterScore.setChapterSummaryScore(record.getScore());
        }
        chapterList.add(chapterScore);
      }
    }
    return chapterList;
  }

  @Override
  public boolean updateUserDailyScoreStatistic(String uid, String module, Long courseId,
      Double score) {
    boolean flag = true;
    try {
      String sScore = MathUtil.getIntStringValue(score);
      String recordTime = DateUtil.SDF_YMD.format(new Date());
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", uid);
      cond.put("moduleId", module);
      cond.put("productId", courseId);
      cond.put("recordTime", recordTime);
      List<UserCourseStatisticDayModel> statisticDayList =
          queryStatisticByDay(cond, CommUtil.getAllField(UserCourseStatisticDayModel.class));
      UserCourseStatisticDayModel entity = new UserCourseStatisticDayModel();
      entity.setScore(Integer.valueOf(sScore));
      Map<String, Object> values = Maps.newHashMap();
      CommUtil.transferFromVO2Map(values, entity);
      if (!CollectionUtils.isEmpty(statisticDayList)) {
        flag = updateStatisticByDay(cond, values);
      } else {
        entity.setModuleId(Integer.valueOf(module));
        entity.setProductId(courseId.longValue());
        entity.setUserId(Long.valueOf(uid));
        entity.setRecordTime(recordTime);
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
          flag = insertStatisticByDay(entity);
        } catch (Exception e) {
          if (e instanceof DuplicateKeyException) {
            flag = updateStatisticByDay(cond, values);
          } else {
            throw new Exception(e);
          }
        }
      }
    } catch (Exception e) {
      LoggerUtil.error(String.format("更新每日分数异常，uid=%s,module=%s,courseId=%s,score=%s", uid, module,
          courseId, score), e);
    }
    return flag;
  }

  @Override
  public List<UserCourseStatisticDayModel> queryStatisticByDay(Map<String, Object> cond,
      Map<String, Object> output) {
    Page<UserCourseStatisticDayModel> page =
        userCourseStatisticDayDao.queryListByCondWithOutPage(cond, output);
    if (null != page) {
      List<UserCourseStatisticDayModel> list = page.getResult();
      return list;
    }
    return null;
  }

  @Override
  public Boolean insertStatisticByDay(UserCourseStatisticDayModel entity) {
    UserCourseStatisticDayModel _entity = userCourseStatisticDayDao.addEntity(entity);
    if (null != _entity) return true;
    return false;
  }

  private Boolean updateStatisticByDay(Map<String, Object> cond, Map<String, Object> values) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(values);
    return userCourseStatisticDayDao.updateEntityByCond(param);
  }

  @Override
  public RedBonus insertRedBonus(RedBonus bonus) {
    if (StringUtils.isBlank(bonus.getId())) bonus.setId(UUID.randomUUID().toString());
    IQueryParam param = new QueryParam();
    param.addInput("redBonusType", bonus.getRedBonusType());
    param.addInput("userId", bonus.getUserId());
    param.addInput("module", bonus.getModule());
    param.addInput("missionId", bonus.getMissionId());
    if (!RedBonusType.Tollgate_complete_item.name().equals(bonus.getRedBonusType()))
      param.addInput("statue", QuesBaseConstant.RED_BONUS_STATUS_INIT);
    param.addOutputs(CommUtil.getAllField(RedBonus.class));
    Page<RedBonus> bonusPage = redBounsDao.findEntityListByCond(param, null);
    if (null != bonusPage && null != bonusPage.getResult() && bonusPage.getResult().size() > 0)
      return bonusPage.getResult().get(0);
    return redBounsDao.addEntity(bonus);
  }

  @Override
  public Boolean updateRedBonusById(RedBonus bonus) {
    return redBounsDao.updateEntityById(bonus);
  }

  @Override
  public Page<RedBonus> queryRedBonusByCond(IQueryParam param) {
    return redBounsDao.findEntityListByCond(param, null);
  }

  @Override
  public RedBonus queryRedBonusById(String id) {
    RedBonus bonus = new RedBonus();
    bonus.setId(id);
    return redBounsDao.findEntityById(bonus);
  }

  @Override
  public int insertUserFinalExam(UserFinalExamRecord record) {
    return userFinalExamRecordDao.insert(record);
  }

  @Override
  public UserFinalExamRecord selectByUidAndExamId(String id, String uid) {
    return userFinalExamRecordDao.selectByUidAndExamId(id, uid);
  }

  @Override
  public List<FinalExamModel> selectFinalExamByCond(String courseId, String userId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("courseId", courseId);
    cond.put("userId", userId);
    // List<UserChapterRecord> firstList = userChapterRecordDao.selectByCond(cond);
    List<UserChapterRecord> firstList = this.queryUserChapterRecord(cond);
    Map<Long, Double> wrapMap = Maps.newHashMap();
    if (null != firstList && !firstList.isEmpty()) {
      for (UserChapterRecord ucr : firstList) {
        if (null != ucr) {
          wrapMap.put(ucr.getItemId(), ucr.getScore());
        }
      }
    }
    List<FinalExamModel> resultList = finalExamDao.selectFinalExamByCond(courseId, userId);
    if (null == resultList || resultList.isEmpty()) {
      return Lists.newArrayList();
    }
    for (FinalExamModel fm : resultList) {
      if (null != fm) {
        fm.setScore(null == wrapMap.get(fm.getId()) ? String.valueOf(0) : wrapMap.get(fm.getId())
            .toString());
      }
    }
    return resultList;
  }

  @Override
  public FinalExamModel selectFinalExamById(Long id) {
    return finalExamDao.selectByPrimaryKey(id);
  }

  @Override
  public int deleteUserFinalExam(String id) {
    return userFinalExamRecordDao.deleteUserFinalExamRecord(id);
  }

  @Override
  public int insertQuesVideoLog(QuesbkAudioLog record) {
    // return quesbkAudioLogDao.addEntity(record);
    return quesbkAudioLogDao.addEntity(record).getId() != null ? 1 : 0;
  }

  @Override
  public List<QuesbkAudioLog> selectQuesVideoLogListByUserId(String userId) {
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    return quesbkAudioLogDao.selectByUserId(inputs);
  }

  @Override
  public List<QuesbkAudioLog> selectQuesVideoLogListByUserIdAndId(String userId, String id) {
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("idUpper", id);
    return quesbkAudioLogDao.selectByUserIdAndId(inputs);
  }

  @Override
  public QuesbkAudioLog findQuesVideoLogById(String id, String userId) {
    QuesbkAudioLog log = new QuesbkAudioLog();
    log.setId(id);
    log.setUserId(userId);
    return quesbkAudioLogDao.findEntityById(log);
  }

  @Override
  public Integer selectCountQuesVideoLogListByUserId(String userId) {
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    return quesbkAudioLogDao.selectCountQuesVideoLogListByUserId(inputs);
  }

  @Override
  public Page<UserScorePointRecord> queryUserScorePointRecord(IQueryParam param) {
    return userScorePointRecordDao.findEntityListByCond(param, null);
  }

  @Override
  public ProductScorePointRule queryProductScorePointRuleById(String ruleId) {
    ProductScorePointRule entity = new ProductScorePointRule();
    entity.setId(ruleId);
    return productScorePointRuleDao.findEntityById(entity);
  }

  @Override
  public Page<ProductScorePointRule> queryProductScorePointRule(IQueryParam param) {
    return productScorePointRuleDao.findEntityListByCond(param, null);
  }

  @Override
  public UserScorePointRecord insertUserScorePointRecord(UserScorePointRecord record) {
    return userScorePointRecordDao.addEntity(record);
  }

  @Override
  public boolean updateUserScorePointRecord(UserScorePointRecord record) {
    return userScorePointRecordDao.updateEntityById(record);
  }

  @Override
  public UserScorePointRecord queryScorePointRecord(String module, String typeCode, Long productId,
      Long userId, Short ruleType) {
    IQueryParam param = new QueryParam();
    param.addOutputs(UserScorePointRecord.class);
    param.addInput("userId", userId);
    param.addInput("productId", productId);
    param.addInput("ruleType", ruleType);
    param.addInput("module", module);
    UserScorePointRecord userScorePointRecord = new UserScorePointRecord();
    Page<UserScorePointRecord> recordPage = queryUserScorePointRecord(param);
    if (null == recordPage || null == recordPage.getResult() || recordPage.getResult().size() == 0) {
      userScorePointRecord.setId(UUID.randomUUID().toString());
      userScorePointRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
      userScorePointRecord.markCreate();
    } else {
      userScorePointRecord = recordPage.getResult().get(0);
      userScorePointRecord.markModify();
    }
    userScorePointRecord.setModule(module);
    userScorePointRecord.setRuleType(ruleType);
    userScorePointRecord.setTypeCode(typeCode);
    userScorePointRecord.setProductId(productId);
    userScorePointRecord.setUserId(userId);
    userScorePointRecord.setModifyTime(new Timestamp(System.currentTimeMillis()));
    return userScorePointRecord;
  }

  @Override
  public void insertOrUpdateUserScorePointRecord(UserScorePointRecord userScorePointRecord) {
    if (QuesBaseConstant.QUES_DOMAIN_OPERATION_CREATE == userScorePointRecord.getOperation()) {
      insertUserScorePointRecord(userScorePointRecord);
    }
    if (QuesBaseConstant.QUES_DOMAIN_OPERATION_MODIFY == userScorePointRecord.getOperation()) {
      updateUserScorePointRecord(userScorePointRecord);
    }
  }

  @Override
  public ProductScorePointRule selectProductScorePointRuleByModule(String module) {
    IQueryParam param = new QueryParam();
    param.addInput("module", module);
    param.addOutputs(RegionModel.class);
    Page<RegionModel> regionPage = regionModelDao.findEntityListByCond(param, null);
    if (null == regionPage || null == regionPage.getResult() || regionPage.getResult().isEmpty()) {
      return null;
    }
    RegionModel region = regionPage.getResult().get(0);
    if (null == region || StringUtils.isBlank(region.getRuleId())) {
      return null;
    }
    ProductScorePointRule entity = new ProductScorePointRule();
    entity.setId(region.getRuleId());
    return productScorePointRuleDao.findEntityById(entity);
  }

}
