package com.xiaodou.resources.service.quesbk.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.cache.product.ProductCache;
import com.xiaodou.resources.cache.quesbk.QuesbkCache;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.dao.product.ProductDao;
import com.xiaodou.resources.dao.product.ProductItemDao;
import com.xiaodou.resources.dao.quesbk.QuesbkBaseMapper;
import com.xiaodou.resources.dao.quesbk.QuesbkExamPaperMapper;
import com.xiaodou.resources.dao.quesbk.QuesbkExamRuleMapper;
import com.xiaodou.resources.dao.quesbk.QuesbkQuesMapper;
import com.xiaodou.resources.dao.quesbk.QuesbkQuesStatisticsMapper;
import com.xiaodou.resources.dao.quesbk.QuesbkQuesTypeMapper;
import com.xiaodou.resources.dao.quesbk.RaiseWrongQuesMapper;
import com.xiaodou.resources.dao.quesbk.TaskScoreDao;
import com.xiaodou.resources.dao.quesbk.UserChapterRecordMapper;
import com.xiaodou.resources.dao.quesbk.UserCourseStatisticDayMapper;
import com.xiaodou.resources.dao.quesbk.UserExamRecordMapper;
import com.xiaodou.resources.dao.quesbk.UserExamTotalMapper;
import com.xiaodou.resources.dao.quesbk.UserStoreRecordCollectMapper;
import com.xiaodou.resources.dao.quesbk.UserStoreRecordMapper;
import com.xiaodou.resources.dao.quesbk.UserWrongRecordCollectMapper;
import com.xiaodou.resources.dao.quesbk.UserWrongRecordMapper;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.model.quesbk.QuesbkQuesStatistics;
import com.xiaodou.resources.model.quesbk.QuesbkQuesType;
import com.xiaodou.resources.model.quesbk.RaiseWrongQues;
import com.xiaodou.resources.model.quesbk.TaskScoreModel;
import com.xiaodou.resources.model.quesbk.UserChapterRecord;
import com.xiaodou.resources.model.quesbk.UserCourseStatisticDayModel;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.model.quesbk.UserStoreRecord;
import com.xiaodou.resources.model.quesbk.UserStoreRecordCollect;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.resources.model.quesbk.UserWrongRecordCollect;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.quesbk.QuesRecordService;
import com.xiaodou.resources.service.quesbk.rule.factory.RuleFactory;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleEntity;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntityParam;
import com.xiaodou.resources.util.ScoreCaculator;
import com.xiaodou.resources.util.ScoreCaculator.UserScoreDetail;
import com.xiaodou.resources.util.StaticInfoProp;
import com.xiaodou.resources.vo.quesbk.ExamDetail;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor;

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
  ProductCache productCache;

  @Resource
  QuesbkCache quesbkCache;

  @Resource
  QuesbkBaseMapper quesbkBaseMapper;

  @Resource
  UserExamRecordMapper userExamRecordMapper;

  @Resource
  UserWrongRecordMapper userWrongRecordMapper;

  @Resource
  UserWrongRecordCollectMapper userWrongRecordCollectMapper;

  @Resource
  UserExamTotalMapper userExamTotalMapper;

  @Resource
  QuesbkExamRuleMapper quesbkExamRuleMapper;

  @Resource
  QuesbkExamPaperMapper quesbkExamPaperMapper;

  @Resource
  UserStoreRecordMapper userStoreRecordMapper;

  @Resource
  UserStoreRecordCollectMapper userStoreRecordCollectMapper;

  @Resource
  QuesbkQuesMapper quesbkQuesMapper;

  @Resource
  QuesbkQuesTypeMapper quesbkQuesTypeMapper;

  @Resource
  SummerTaskExecutor quesbkServiceTaskExecutor;

  @Resource
  ProductDao productDao;

  @Resource
  ProductItemDao productItemDao;

  @Resource
  QuesbkQuesStatisticsMapper qsuesbkQuesStatisticsMapper;

  @Resource
  RaiseWrongQuesMapper raiseWrongQuesMapper;
  @Resource
  UserChapterRecordMapper userChapterRecordMapper;

  @Resource
  UserCourseStatisticDayMapper userCourseStatisticDayMapper;

  @Resource
  QuesRecordService quesRecordService;

  @Resource
  TaskScoreDao taskScoreDao;

  @Resource
  QueueService queueService;

  @Override
  public ProductModel queryProduct(String productId) {
    CourseProduct product = productCache.getProductById(productId);
    if (null == product) {
      ProductModel _product = new ProductModel();
      _product.setId(Long.parseLong(productId));
      return productDao.findEntityById(_product);
    }
    return product;
  }

  @Override
  public List<ProductItemModel> queryScoreItemList(String productId, String userId) {
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("userId", userId);
    param.addInput("productId", productId);
    List<Integer> resourceTypeList = Lists.newArrayList();
    resourceTypeList.add(ResourceType.EXAM.getTypeId());
    resourceTypeList.add(ResourceType.TALK.getTypeId());
    resourceTypeList.add(ResourceType.TASK.getTypeId());
    resourceTypeList.add(ResourceType.FINAL.getTypeId());
    param.addInput("resourceTypeList", resourceTypeList);
    param.addInput("userId", userId);
    Page<ProductItemModel> itemPage = productItemDao.casecadeQueryByCond(param, null);
    if (null == itemPage || null == itemPage.getResult() || itemPage.getResult().size() == 0)
      return null;
    return itemPage.getResult();
  }

  @Override
  public List<ProductItemModel> queryChapterList(String productId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("resourceType", ResourceType.CHAPTER.getTypeId());
    param.addInput("isChapter", QuesBaseConstant.YES);
    Page<ProductItemModel> chapterPage = productItemDao.findEntityListByCond(param, null);
    if (null == chapterPage || null == chapterPage.getResult()
        || chapterPage.getResult().size() == 0) return null;
    return chapterPage.getResult();
  }

  @Override
  public List<ProductItemModel> queryItemList(String productId, String chapterId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("resourceType", ResourceType.CHAPTER.getTypeId());
    param.addInput("isItem", QuesBaseConstant.YES);
    param.addOutputs(CommUtil.getAllField(ProductItemModel.class));
    if (StringUtils.isNotBlank(chapterId)) param.addInput("parentId", chapterId);
    Page<ProductItemModel> chapterPage = productItemDao.findEntityListByCond(param, null);
    if (null == chapterPage || null == chapterPage.getResult()
        || chapterPage.getResult().size() == 0) return null;
    return chapterPage.getResult();
  }

  @Override
  public List<ProductItemModel> queryChapterItemList(String productId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("resourceType", ResourceType.CHAPTER.getTypeId());
    Page<ProductItemModel> chapterPage = productItemDao.findEntityListByCond(param, null);
    if (null == chapterPage || null == chapterPage.getResult()
        || chapterPage.getResult().size() == 0) return null;
    return chapterPage.getResult();
  }

  @Override
  public ProductItemModel queryItem(String id, String userId) {
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("userId", userId);
    param.addInput("id", id);
    param.addInput("userId", userId);
    Page<ProductItemModel> itemPage = productItemDao.casecadeQueryByCond(param, null);
    if (null == itemPage || null == itemPage.getResult() || itemPage.getResult().size() == 0)
      return null;
    return itemPage.getResult().get(0);
  }

  @Override
  public ProductItemModel queryItem(String id) {
    ProductItemModel item = new ProductItemModel();
    item.setId(Long.parseLong(id));
    return productItemDao.findEntityById(item);
  }

  @Override
  public UserExamTotal queryExamTotal(String uid, String courseId) {
    if (StringUtils.isBlank(uid, courseId)) return null;
    return userExamTotalMapper.selectByUidAndSubjectId(uid, courseId);
  }

  @Override
  public Integer countExamTotal(String courseId) {
    return userExamTotalMapper.selectAllUserCountBySubjectId(courseId);
  }

  @Override
  public Boolean updateExamTotal(UserExamTotal myExamTotal) {
    return userExamTotalMapper.updateByPrimaryKeySelective(myExamTotal) == 1;
  }

  @Override
  public Boolean insertExamTotal(UserExamTotal myExamTotal) {
    return userExamTotalMapper.insertSelective(myExamTotal) == 1;
  }

  @Override
  public List<QuesbkExamPaper> queryExamPaperList(String courseId, String examType) {
    return quesbkExamPaperMapper.selectBySubjectIdAndExamType(courseId, examType);
  }

  @Override
  public QuesbkExamPaper queryExamPaper(String paperId) {
    return quesbkExamPaperMapper.selectByPrimaryKey(paperId);
  }

  @Override
  public List<QuesbkQues> queryQuesList(List<String> quesIds, String userId, String courseId) {
    Map<String, Object> paraMap = Maps.newHashMap();
    paraMap.put("list", quesIds);
    paraMap.put("userId", userId);
    paraMap.put("courseId", courseId);
    return quesbkQuesMapper.selectByPrimaryKeyList(paraMap);
  }

  @Override
  public QuesbkQues queryQues(String quesId, String courseId) {
    return quesbkQuesMapper.selectByPrimaryKey(quesId, courseId);
  }

  @Override
  public Boolean updateContinueExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordMapper.updateByPrimaryKey(userExamRecord) == 1;
  }

  @Override
  public Boolean updateResetExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordMapper.updateByPrimaryKeySelective(userExamRecord) == 1;
  }

  @Override
  public Boolean insertExamRecord(UserExamRecord userExamRecord) {
    return userExamRecordMapper.insert(userExamRecord) == 1;
  }

  @Override
  public UserExamRecord queryExamRecord(String recordId) {
    return userExamRecordMapper.selectByPrimaryKey(recordId);
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String uid, String courseId) {
    return userExamRecordMapper.selectByUidAndSubjectId(uid, courseId);
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String uid, String courseId, String examTypeId) {
    return userExamRecordMapper.selectByUidSubjectIdAndExamTypeId(uid, courseId, examTypeId);
  }

  @Override
  public void updateUserScore(String productId, String userId) {
    // 获取权重
    ProductModel product = queryProduct(productId);
    if (null == product) return;
    ScoreCaculator caculator = new ScoreCaculator(product);
    // 获取item采分点集合，分类
    List<ProductItemModel> scoreItemList = queryScoreItemList(productId, userId);
    if (null == scoreItemList || scoreItemList.size() == 0) return;
    List<ProductItemModel> talkList = Lists.newArrayList(), taskList = Lists.newArrayList(), examList =
        Lists.newArrayList(), finalList = Lists.newArrayList();
    List<Long> itemIdList = Lists.newArrayList();
    for (ProductItemModel scoreItem : scoreItemList) {
      ResourceType type = ResourceType.getByTypeId(scoreItem.getResourceType().intValue());
      itemIdList.add(scoreItem.getId());
      switch (type) {
        case TALK:
          talkList.add(scoreItem);
          break;
        case TASK:
          taskList.add(scoreItem);
          break;
        case EXAM:
          examList.add(scoreItem);
          break;
        case FINAL:
          finalList.add(scoreItem);
          break;
        default:
          break;
      }
    }
    // 查询taskScore，计算各采分点平均成绩
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("userId", userId);
    param.addInput("taskIdList", itemIdList);
    param.addOutputs(CommUtil.getAllField(TaskScoreModel.class));
    Page<TaskScoreModel> taskScorePage = taskScoreDao.findEntityListByCond(param, null);
    if (null == taskScorePage || null == taskScorePage.getResult()
        || taskScorePage.getResult().size() == 0) return;
    Map<Long, TaskScoreModel> taskScoreMap = Maps.newHashMap();
    for (TaskScoreModel taskScore : taskScorePage.getResult())
      taskScoreMap.put(taskScore.getTaskId(), taskScore);
    UserScoreDetail scoreDetail = new UserScoreDetail();
    scoreDetail.setTalkScore(getScore(talkList, taskScoreMap));
    scoreDetail.setTaskScore(getScore(taskList, taskScoreMap));
    scoreDetail.setExamScore(getScore(examList, taskScoreMap));
    scoreDetail.setFinalScore(getScore(finalList, taskScoreMap));
    // 根据权重计算总成绩，更新examTotal表
    Double score = caculator.caculate(scoreDetail);
    UserExamTotal examTotal = queryExamTotal(userId, productId);
    UserExamTotal myExamTotal = new UserExamTotal();
    myExamTotal.setScore(score);
    myExamTotal.setChapterScore(FastJsonUtil.toJson(scoreDetail));
    if (null == examTotal) {
      myExamTotal.setUserId(userId);
      myExamTotal.setCourseId(productId);
      queueService.addExamTotal(myExamTotal);
    } else {
      myExamTotal.setId(examTotal.getId());
      updateExamTotal(examTotal);
    }
  }

  private Double getScore(List<ProductItemModel> scoreItemList,
      Map<Long, TaskScoreModel> taskScoreMap) {
    Double totalScore = 0d;
    if (null == scoreItemList || scoreItemList.size() == 0) return totalScore;
    for (ProductItemModel scoreItem : scoreItemList) {
      TaskScoreModel taskScoreModel = taskScoreMap.get(scoreItem.getId());
      if (null != taskScoreModel && null != taskScoreModel.getScore())
        totalScore += taskScoreModel.getScore();
    }
    return totalScore / scoreItemList.size();
  }

  @Override
  public List<UserExamRecord> queryExamRecordListByItemId(String uid, String courseId, String itemId) {
    return userExamRecordMapper.selectByUidProductIdAndItemId(uid, courseId, itemId);
  }

  @Override
  public List<UserExamRecord> queryNotInExamRecordList(String uid, String courseId,
      List<String> examTypeId, List<String> paperIdList) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", uid);
    params.put("courseId", courseId);
    params.put("examTypeIdList", examTypeId);
    params.put("paperIdList", paperIdList);
    return userExamRecordMapper.selectByNotUidSubjectIdAndExamTypeId(params);
  }

  @Override
  public List<UserExamRecord> queryExamRecordList(String paperId) {
    return userExamRecordMapper.selectByPaperId(paperId);
  }

  @Override
  public List<String> queryTodayQuesIdList(String uid, List<String> courseIdList) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("uid", uid);
    cond.put("courseIdList", courseIdList);
    List<String> todayQuesIdList = quesbkExamPaperMapper.queryTodayExamPaperList(cond);
    if (null == todayQuesIdList || todayQuesIdList.size() == 0) return null;
    List<String> quesIdList = Lists.newArrayList();
    for (String todayQuesId : todayQuesIdList) {
      if (StringUtils.isJsonBlank(todayQuesId)) continue;
      quesIdList.addAll(FastJsonUtil.fromJsons(todayQuesId, new TypeReference<List<String>>() {}));
    }
    return quesIdList;
  }

  @Override
  public List<String> queryTotalQuesIdList(String uid, List<String> courseIdList) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("uid", uid);
    cond.put("courseIdList", courseIdList);
    List<String> todayQuesIdList = quesbkExamPaperMapper.queryTotalExamPaperList(cond);
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
    return userWrongRecordMapper.findEntityListByCond(input, page);
  }

  @Override
  public Integer countTotalQuesRank(Integer count, String uid, String courseId) {
    if (StringUtils.isBlank(uid, courseId) || null == count) return -1;
    return userExamTotalMapper.queryTotalRankByCond(count.toString(), uid, courseId);
  }

  @Override
  public Integer countRightQuesRank(Integer count, String uid, String courseId) {
    if (StringUtils.isBlank(uid, courseId) || null == count) return -1;
    return userExamTotalMapper.queryRightRankByCond(count.toString(), uid, courseId);
  }

  @Override
  public Page<UserWrongRecord> queryAbstractWrongRecordList(Map<String, Object> cond,
      Page<UserWrongRecord> page) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    return userWrongRecordMapper.findEntityByCond(input, page);
  }

  @Override
  public Integer countWrongRecord(Map<String, Object> cond) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("cond", cond);
    return userWrongRecordMapper.queryCountByCond(input);
  }

  @Override
  public List<UserWrongRecordCollect> queryWrongRecordCollectList(String uid, String courseId) {
    return userWrongRecordCollectMapper.selectByUserIdAndCourseId(uid, courseId);
  }

  @Override
  public UserWrongRecordCollect queryWrongRecordCollect(String uid, String courseId, String itemId) {
    return userWrongRecordCollectMapper.selectByUserIdAndCourseIdItemId(uid, courseId, itemId);
  }

  @Override
  public int updateWrongRecordCollect(UserWrongRecordCollect userWrongRecordCollect) {
    return userWrongRecordCollectMapper.updateByUSISelective(userWrongRecordCollect);
  }

  @Override
  public void insertWrongRecordCollect(UserWrongRecordCollect entity) {
    userWrongRecordCollectMapper.insertEntity(entity);
  }

  @Override
  public List<UserStoreRecord> queryStoreRecordList(String uid, String courseId, String chapterId) {
    return userStoreRecordMapper.selectByUSC(uid, courseId, chapterId);
  }

  @Override
  public UserStoreRecord queryStoreRecord(String uid, String courseId, String chapterId,
      String questionId) {
    return userStoreRecordMapper.selectByUSCQ(uid, courseId, chapterId, questionId);
  }

  @Override
  public Integer countStoreRecord(String uid, String courseId, String chapterId) {
    return userStoreRecordMapper.queryCountByUSC(uid, courseId, chapterId);
  }

  @Override
  public List<UserStoreRecordCollect> queryStoreRecordCollectList(String uid, String courseId) {
    return userStoreRecordCollectMapper.selectByUidAndSubjectId(uid, courseId);
  }

  @Override
  public UserStoreRecordCollect queryStoreRecordCollect(String uid, String courseId, String itemId) {
    return userStoreRecordCollectMapper.selectByUSI(uid, courseId, itemId);
  }

  @Override
  public Boolean insertStoreRecordCollect(UserStoreRecordCollect record) {
    return userStoreRecordCollectMapper.insert(record) == 1;
  }

  @Override
  public int updateStoreRecordCollet(UserStoreRecordCollect userStoreRecordCollect) {
    return userStoreRecordCollectMapper.updateByUSISelective(userStoreRecordCollect);
  }

  @Override
  public Boolean insertStoreRecord(UserStoreRecord record) {
    return userStoreRecordMapper.insert(record) == 1;
  }

  @Override
  public List<UserStoreRecord> queryStoreRecordList(String uid, String courseId) {
    return userStoreRecordMapper.selectByUidAndSubjectId(uid, courseId);
  }

  /*
   * @Override public List<UserStoreRecord> queryStoreRecordList(UserStoreRecord userStoreRecord) {
   * return userStoreRecordMapper.selectByUSCList(userStoreRecord); }
   */
  @Override
  public List<UserStoreRecord> queryStoreRecordList(Map<String, Object> input) {
    return userStoreRecordMapper.selectByUSCList(input);
  }

  @Override
  public List<QuesbkExamRule> queryExamRuleList(String productId, String examTypeId) {
    return quesbkExamRuleMapper.selectByProductIdAndExamType(productId, examTypeId);
  }

  @Override
  public UserExamRecord queryExamRecord(String paperId, String userId) {
    return userExamRecordMapper.selectByUidAndPaperId(paperId, userId);
  }

  @Override
  public List<UserExamRecord> selectExamCostByExamCost(Map<String, Object> params) {
    return userExamRecordMapper.selectExamCostByExamCost(params);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List<Object> queryExamQuestionIdList(ExamDetail pojo, QuesbkExamPaper paper)
      throws Exception {
    List<QuesbkExamRule> examRuleList = Lists.newArrayList();
    if (StaticInfoProp.isNotDefaultRule(pojo.getExamType()))
      examRuleList =
          quesbkExamRuleMapper.selectByProductIdAndExamType(pojo.getCourseId(), pojo.getExamType());
    List<QuesbkQuesType> quesTypeList = queryQuesTypeList();
    IRuleEntity rule = RuleFactory.getRule(pojo.getExamType(), examRuleList, paper);
    if (null == rule) return null;
    return rule.getQuestions(new RuleEntityParam(this, quesbkServiceTaskExecutor, quesTypeList,
        pojo.getUid(), pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo
            .getPriorQues(), pojo.getLastQuesId()));
  }

  @Override
  public List<QuesbkQuesType> queryQuesTypeList() {
    List<QuesbkQuesType> quesTypeLst = quesbkCache.getQuesType();
    if (null == quesTypeLst) {
      return quesbkQuesTypeMapper.selectQuesType();
    }
    return quesTypeLst;
  }

  @Override
  public List<QuesbkQues> queryQuesList(Rule rule) {
    return quesbkQuesMapper.selectByRule(rule);
  }

  @Override
  public List<QuesbkQues> queryAbstractQuesList(Rule rule) {
    return quesbkQuesMapper.selectAbstractByRule(rule);
  }

  @Override
  public void updateWrongTimes(UserWrongRecord oldRecord) {
    userWrongRecordMapper.updateWrongTimes(oldRecord);
  }

  @Override
  public void updateRightTimes(UserWrongRecord oldRecord) {
    userWrongRecordMapper.updateRightTimes(oldRecord);
  }

  @Override
  public void insertWrongRecord(UserWrongRecord wrongRecord) {
    userWrongRecordMapper.addEntity(wrongRecord);
  }

  @Override
  public List<QuesbkQues> queryAbstractQuesList(List<String> quesIds, String courseId) {
    Map<String, Object> paraMap = Maps.newHashMap();
    paraMap.put("list", quesIds);
    paraMap.put("courseId", courseId);
    return quesbkQuesMapper.selectAbstractByPrimaryKeyList(paraMap);
  }

  @Override
  public QuesbkQuesStatistics queryQuesStatistic(String quesId, String courseId) {
    return qsuesbkQuesStatisticsMapper.selectByQuesId(quesId, courseId);
  }

  @Override
  public void insertQuesStatistic(QuesbkQuesStatistics statistic) {
    qsuesbkQuesStatisticsMapper.insertSelective(statistic);
  }

  @Override
  public void updateQuesStatistic(QuesbkQuesStatistics statistic) {
    qsuesbkQuesStatisticsMapper.updateByExam(statistic);
  }

  @Override
  public void insertExamPaper(QuesbkExamPaper paper) {
    quesbkExamPaperMapper.insert(paper);
  }

  @Override
  public Boolean insertRaiseWrongQues(RaiseWrongQues entity) {
    return null != raiseWrongQuesMapper.addEntity(entity);
  }

  @Override
  public Boolean changeWrongQues(UserWrongRecord record) {
    return userWrongRecordMapper.updateEntity(record);
  }

  @Override
  public Boolean changeStoreQues(UserStoreRecord record) {
    return 1 == userStoreRecordMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public List<ProductModel> queryBuyCourseByCond(Map<String, Object> params) {
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("userId", params.get("userId"));
    param.addInputs(params);
    param.addOutputs(CommUtil.getAllField(ProductModel.class));
    Page<ProductModel> productPage = productDao.casecadeQueryByCond(param, null);
    if (null == productPage || null == productPage.getResult()
        || productPage.getResult().size() == 0) return null;
    return productPage.getResult();
  }

  @Override
  public Boolean insertUserChapterRecord(UserChapterRecord record) {
    return 1 == userChapterRecordMapper.insertSelective(record);
  }

  @Override
  public Boolean updateUserChapterRecord(UserChapterRecord record) {
    return 1 == userChapterRecordMapper.updateByPrimaryKey(record);
  }

  @Override
  public UserChapterRecord queryUserChapterRecord(String id) {
    return userChapterRecordMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<UserExamTotal> queryExamTotal(Map<String, Object> cond) {
    return userExamTotalMapper.queryByCond(cond);
  }

  @Override
  public List<UserChapterRecord> queryUserChapterRecord(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return userChapterRecordMapper.selectByCond(cond);
  }

  @Override
  public List<UserCourseStatisticDayModel> queryStatisticByDay(Map<String, Object> cond,
      Map<String, Object> output) {
    Page<UserCourseStatisticDayModel> page =
        userCourseStatisticDayMapper.queryListByCondWithOutPage(cond, output);
    if (null != page) {
      List<UserCourseStatisticDayModel> list = page.getResult();
      return list;
    }
    return null;
  }

  @Override
  public Boolean insertStatisticByDay(UserCourseStatisticDayModel entity) {
    UserCourseStatisticDayModel _entity = userCourseStatisticDayMapper.addEntity(entity);
    if (null != _entity) return true;
    return false;
  }

  @Override
  public Boolean updateStatisticByDay(Map<String, Object> cond, UserCourseStatisticDayModel entity) {
    return userCourseStatisticDayMapper.updateEntityByCond(cond, entity);
  }

  @Override
  public Page<TaskScoreModel> queryTaskScoreModel(IQueryParam param) {
    return taskScoreDao.findEntityListByCond(param, null);
  }

  @Override
  public void insertTaskScoreModel(TaskScoreModel model) {
    taskScoreDao.addEntity(model);
  }

  @Override
  public void updateTaskScoreModel(TaskScoreModel model) {
    taskScoreDao.updateEntityById(model);
  }

}
