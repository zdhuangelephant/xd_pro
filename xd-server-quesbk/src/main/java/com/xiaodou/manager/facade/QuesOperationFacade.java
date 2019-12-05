package com.xiaodou.manager.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserCourseStatisticDayModel;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamTotal;
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
import com.xiaodou.domain.product.QuesbkAudioLog;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.domain.product.QuesbkQuesStatistics;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.domain.product.RaiseWrongQues;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.vo.request.ExamDetailPojo;
import com.xiaodou.vo.request.ExamDetailPojo_v1_3_8;

/**
 * @name @see com.xiaodou.service.facade.QuesOperationFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库数据源上层Facade接口
 * @version 1.0
 */
public interface QuesOperationFacade {

  /** 产品 */

  CourseProduct queryProduct(String productId);

  /**
   * 根据模块ID和专业ID获取产品列表
   */
  List<CourseProduct> queryProductList(String module, String typeCode);

  /**
   * 根据模块ID和课程ID获取typeCode列表
   */
  List<String> queryTypeCodeList(String module, String courseId);

  /**
   * 根据产品ID获取产品章节列表
   */
  List<CourseProductItem> queryChapterItemList(String courseId);

  /**
   * 根据产品ID和用户ID获取产品章节列表
   */
  List<CourseProductItem> queryChapterItemList(String courseId, String userId);

  /**
   * 根据课程ID获取课程章列表（找章信息）
   */
  List<CourseProductItem> queryChapterList(String courseId);

  /**
   * 根据课程ID和章ID获取节列表
   */
  List<CourseProductItem> queryItemList(String courseId, String chapterId);

  /**
   * 根据课程ID和章ID和节ID获取节信息
   */
  CourseProductItem queryItem(String courseId, String chapterId, String itemId);

  /**
   * 根据课程ID和章ID获取章信息
   */
  CourseProductItem queryChapter(String courseId, String chapterId);

  /** 用户统计 */

  /**
   * 根据用户ID和课程ID获取用户练习统计情况
   */
  UserExamTotal queryExamTotal(String uid, String courseId);

  /**
   * 根据指定条件查询练习统计列表
   * 
   * @param cond
   */
  List<UserExamTotal> queryExamTotal(Map<String, Object> cond);

  /**
   * 根据课程ID获取用户参与人数
   */
  Integer countExamTotal(String courseId);

  /**
   * 根据主键更新选择字段
   */
  Boolean updateExamTotal(UserExamTotal myExamTotal);

  /**
   * 插入新的用户练习统计情况
   */
  Boolean insertExamTotal(UserExamTotal myExamTotal);

  Boolean updateOrAddEntity(UserExamTotal value, Map<String, Object> column);

  /** 练习规则 */

  /**
   * 根据产品ID和练习类型ID获取练习规则列表
   */
  List<QuesbkExamRule> queryExamRuleList(String productId, String examTypeName);

  /**
   * 2017/11/13_ADD_BY zhaodan 将初始化规则列表从组卷逻辑中抽离出来,方便1.3.8版本以后出题逻辑整体变更为命题蓝图出圈后对老版本的兼容
   * 根据科目ID和练习类型ID获取练习问题ID列表
   * 
   * @throws Exception
   */
  List<Object> queryExamQuestionIdList_v1_3_8(ExamDetailPojo_v1_3_8 pojo, QuesbkExamPaper paper)
      throws Exception;

  /**
   * 根据科目ID和练习类型ID获取练习问题ID列表
   * 
   * @throws Exception
   */
  List<Object> queryExamQuestionIdList(ExamDetailPojo pojo, List<QuesbkExamRule> ruleList,
      QuesbkExamPaper paper) throws Exception;

  /** 练习试卷 */

  /**
   * 根据课程ID和练习类型获取试卷
   */
  List<QuesbkExamPaper> queryExamPaperList(String courseId, String examType);

  /**
   * 查询指定试卷内容
   */
  QuesbkExamPaper queryExamPaper(String paperId);

  /**
   * 插入新试卷
   */
  void insertExamPaper(QuesbkExamPaper paper);

  /** 题目 */

  /**
   * 根据题目主键列表获取题目列表(含用戶练习情况的全量信息)
   */
  List<QuesbkQues> queryQuesList(List<String> quesIds, String userId, String courseId);

  /**
   * 根据题目主键列表获取题目列表(题目概要信息)
   */
  List<QuesbkQues> queryAbstractQuesList(List<String> quesIds, String courseId);

  /**
   * 根据题目主键获取题目详情
   */
  QuesbkQues queryQues(String quesId, String courseId);

  /**
   * 根据出题规则
   */
//  List<QuesbkQues> queryQuesList(Rule rule);

  /**
   * 根据出题规则
   */
  List<Long> queryQuesIdList(Rule rule);

  /**
   * 根据IDList出题
   */
  List<QuesbkQues> queryQuesListByIdList(String userId, String courseId, List<Long> quesIdList);

  /**
   * 根据出题规则
   */
  List<QuesbkQues> queryAbstractQuesList(Rule rule);

  /** 题目类型 */

  /**
   * 根据课程ID获取题目类型列表
   */
  List<QuesbkQuesType> queryQuesTypeList();

  /** 练习记录 */

  /**
   * 根据主键跟新用户练习记录
   */
  Boolean updateContinueExamRecord(UserExamRecord userExamRecord);

  /**
   * 根据主键跟新用户练习记录
   */
  Boolean updateResetExamRecord(UserExamRecord userExamRecord);

  /**
   * 插入新的用户练习记录
   */
  Boolean insertExamRecord(UserExamRecord userExamRecord);

  /**
   * 根据用户ID和课程ID获取用户练习记录列表
   */
  List<UserExamRecord> queryExamRecordList(String uid, String courseId);

  /**
   * 根据用户ID和课程ID获取用户练习记录列表
   */
  List<UserExamRecord> queryExamRecordList(String uid, String courseId, String examTypeId);

  /**
   * 查詢不在ID列表中的挑战记录
   */
  List<UserExamRecord> queryNotInExamRecordList(String uid, String courseId,
      List<String> examTypeId, List<String> paperIdList);


  /**
   * 根据试卷ID获取用户练习记录列表
   */
  List<UserExamRecord> queryExamRecordList(String paperId);

  /**
   * 根据用户ID和课程ID获取用户今日练习题目数量
   */
  Integer queryTodayQuesCount(String uid, List<String> courseIdList);

  /**
   * 根据用户ID和课程ID获取用户练习题目Id列表
   */
  List<String> queryTotalQuesIdList(String uid, List<String> courseIdList);

  /**
   * 根据主键ID获取用户练习记录列表
   */
  UserExamRecord queryExamRecord(String paperId, String userId);

  /**
   * 查询近期条件天数的学习时长
   */
  List<UserExamRecord> selectExamCostByExamCost(Map<String, Object> params);

  /** 错题记录 */

  /**
   * 根据查询条件获取用户错题章节分布列表
   */
  List<UserWrongRecordCollect> queryWrongRecordCollectList(String uid, String courseId);

  /**
   * 根据查询条件获取用户错题章节分布列表
   */
  UserWrongRecordCollect queryWrongRecordCollect(String uid, String courseId, String itemId);

  /**
   * 根据用户ID、课程ID、节ID修改用户错题统计记录数
   */
  int updateWrongRecordCollect(UserWrongRecordCollect userWrongRecordCollect);

  /**
   * 新增错题统计记录数
   */
  void insertWrongRecordCollect(UserWrongRecordCollect entity);

  /**
   * 根据查询条件获取用户错题列表
   */
  Page<UserWrongRecord> queryAbstractWrongRecordList(Map<String, Object> cond,
      Page<UserWrongRecord> page);

  /**
   * 根据查询条件获取用户错题统计记录
   */
  Page<UserWrongRecord> queryWrongRecordList(Map<String, Object> cond, Page<UserWrongRecord> page);

  /**
   * 根据用户ID查询用户做题数量排名
   */
  Integer countTotalQuesRank(Integer count, String uid, String courseId);

  /**
   * 根据用户ID查询用户正确答题数量排名
   */
  Integer countRightQuesRank(Integer count, String uid, String courseId);

  /**
   * 查询章节错题数量
   */
  Integer countWrongRecord(Map<String, Object> cond);

  /**
   * 更新练习题目的错误次数
   */
  void updateWrongTimes(UserWrongRecord oldRecord);

  /**
   * 更新练习题目的正确次数
   */
  void updateRightTimes(UserWrongRecord oldRecord);

  /**
   * 新增一条练习记录
   */
  void insertWrongRecord(UserWrongRecord wrongRecord);

  /** 用戶統計分析信息 */

  /**
   * 根据问题ID查询统计分析信息
   */
  QuesbkQuesStatistics queryQuesStatistic(String quesId, String courseId);

  /**
   * 插入统计分析信息实体
   */
  void insertQuesStatistic(QuesbkQuesStatistics statistic);

  /**
   * 跟新统计分析实体
   */
  void updateQuesStatistic(QuesbkQuesStatistics statistic);

  /** 用戶挑战模块 */

  /**
   * 插入挑战记录
   */
  Boolean insertChallengeRecord(ChallengeRecord record);

  /**
   * 更新挑战记录
   */
  Boolean updateChallengeRecord(ChallengeRecord record);

  /**
   * 根据ID查询挑战记录
   */
  ChallengeRecord queryChallengeRecord(String id);

  /**
   * 根据条件查询挑战记录
   */
  List<ChallengeRecord> queryChallengeRecord(Map<String, Object> record);

  /**
   * 插入举报错题记录
   */
  Boolean insertRaiseWrongQues(RaiseWrongQues raise);

  /**
   * 修改错题状态
   */
  void changeWrongQues(UserWrongRecord record);

  /**
   * 查询用户已购课程
   */
  List<CourseProduct> queryBuyCourseByCond(Map<String, Object> params);

  List<CourseProduct> selectBuyProductByCond0(Map<String, Object> params);

  /** 用户闯关模块 */
  /**
   * 插入闯关记录
   */
  Boolean insertUserChapterRecord(UserChapterRecord record);

  /**
   * 更新闯关记录
   */
  Boolean updateUserChapterRecord(UserChapterRecord record);

  /**
   * 根据ID查询闯关记录
   */
  UserChapterRecord queryUserChapterRecord(String id);

  /**
   * 根据条件查询闯关记录
   */
  List<UserChapterRecord> queryUserChapterRecord(Map<String, Object> input);

  Page<ChallengeRobot> queryChallengeRobot(Map<String, Object> cond, Page<ChallengeRobot> page);

  void updateUserScoreNoEvent(String module, Long courseId, String uid, String... typeCode);

  void updateUserScoreWithEvent(String module, Long courseId, String uid, String... typeCode);

  /* 每日得分统计 xd_user_course_statistic_day */

  List<UserCourseStatisticDayModel> queryStatisticByDay(Map<String, Object> cond,
      Map<String, Object> output);

  Boolean insertStatisticByDay(UserCourseStatisticDayModel entity);

  RedBonus insertRedBonus(RedBonus bonus);

  Boolean updateRedBonusById(RedBonus bonus);

  RedBonus queryRedBonusById(String id);

  Page<RedBonus> queryRedBonusByCond(IQueryParam param);

  int insertUserFinalExam(UserFinalExamRecord record);

  UserFinalExamRecord selectByUidAndExamId(String id, String uid);

  int deleteUserFinalExam(String id);

  List<FinalExamModel> selectFinalExamByCond(String courseId, String userId);

  FinalExamModel selectFinalExamById(Long id);

  boolean updateUserDailyScoreStatistic(String userId, String module, Long courseId, Double score);

  int insertQuesVideoLog(QuesbkAudioLog record);

  List<QuesbkAudioLog> selectQuesVideoLogListByUserId(String userId);

  List<QuesbkAudioLog> selectQuesVideoLogListByUserIdAndId(String userId, String id);

  QuesbkAudioLog findQuesVideoLogById(String id, String userId);

  Integer selectCountQuesVideoLogListByUserId(String uid);

  Page<UserScorePointRecord> queryUserScorePointRecord(IQueryParam param);

  UserScorePointRecord insertUserScorePointRecord(UserScorePointRecord record);

  boolean updateUserScorePointRecord(UserScorePointRecord record);

  ProductScorePointRule queryProductScorePointRuleById(String ruleId);

  Page<ProductScorePointRule> queryProductScorePointRule(IQueryParam param);

  /**
   * 查询用户计分点明细记录
   * 
   * @param module 地域码
   * @param typeCode 专业码
   * @param productId 产品ID
   * @param userId 用户ID
   * @param ruleType 规则类型码
   * @return 用户计分点记录
   */
  UserScorePointRecord queryScorePointRecord(String module, String typeCode, Long productId,
      Long userId, Short ruleType);

  /**
   * 插入或更新用户得分点明细记录
   * 
   * @param userScorePointRecord 用户计分点记录
   */
  void insertOrUpdateUserScorePointRecord(UserScorePointRecord userScorePointRecord);

  ProductScorePointRule selectProductScorePointRuleByModule(String module);
}
