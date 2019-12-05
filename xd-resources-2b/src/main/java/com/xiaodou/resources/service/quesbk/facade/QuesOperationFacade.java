package com.xiaodou.resources.service.quesbk.facade;

import java.util.List;
import java.util.Map;

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
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.vo.quesbk.ExamDetail;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

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

  /**
   * 根据产品ID查询产品详情
   */
  ProductModel queryProduct(String productId);

  /**
   * 根据产品ID获取产品采分点列表
   */
  List<ProductItemModel> queryScoreItemList(String productId, String userId);

  /**
   * 根据课程ID获取课程章列表（找章信息）
   */
  List<ProductItemModel> queryChapterList(String productId);

  /**
   * 根据课程ID和章ID获取节列表
   */
  List<ProductItemModel> queryItemList(String productId, String chapterId);

  /**
   * 根据产品ID获取产品全部章节信息
   */
  List<ProductItemModel> queryChapterItemList(String productId);

  /**
   * 根据节ID和用户ID获取节点信息
   * 
   * @param id 节点ID
   * @param userId 用户ID
   * @return 节信息
   */
  ProductItemModel queryItem(String id, String userId);

  /**
   * 根据节ID获取节点信息
   * 
   * @param id 节点ID
   * @return 节信息
   */
  ProductItemModel queryItem(String id);

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

  /** 练习规则 */

  /**
   * 根据产品ID和练习类型ID获取练习规则列表
   */
  List<QuesbkExamRule> queryExamRuleList(String productId, String examTypeName);

  /**
   * 根据科目ID和练习类型ID获取练习问题ID列表
   * 
   * @throws Exception
   */
  List<Object> queryExamQuestionIdList(ExamDetail pojo, QuesbkExamPaper paper) throws Exception;

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
  List<QuesbkQues> queryQuesList(Rule rule);

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
   * 根据用户ID和产品ID和节ID获取用户练习记录列表
   */
  List<UserExamRecord> queryExamRecordListByItemId(String uid, String courseId, String itemId);

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
   * 更新用户成绩
   */
  void updateUserScore(String porductId, String userId);

  /**
   * 根据用户ID和课程ID获取用户今日练习题目Id列表
   */
  List<String> queryTodayQuesIdList(String uid, List<String> courseIdList);

  /**
   * 根据用户ID和课程ID获取用户练习题目Id列表
   */
  List<String> queryTotalQuesIdList(String uid, List<String> courseIdList);

  /**
   * 根据主键ID获取用户练习记录列表
   */
  UserExamRecord queryExamRecord(String recordId);

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

  /** 收藏记录 */

  /**
   * 根据用户ID、课程ID获取用户收藏统计记录
   */
  List<UserStoreRecordCollect> queryStoreRecordCollectList(String uid, String courseId);

  /**
   * 根据用户ID、课程ID、节ID获取用户收藏统计记录
   */
  UserStoreRecordCollect queryStoreRecordCollect(String uid, String courseId, String itemId);

  /**
   * 插入新的收藏记录
   */
  Boolean insertStoreRecordCollect(UserStoreRecordCollect record);

  /**
   * 根据用户ID、课程ID、节ID修改用户收藏统计记录数
   */
  int updateStoreRecordCollet(UserStoreRecordCollect userStoreRecordCollect);

  /**
   * 根据用户ID、课程ID、章节ID获取用户收藏记录列表
   */
  List<UserStoreRecord> queryStoreRecordList(String uid, String courseId, String chapterId);

  /**
   * 根据用户ID、课程ID、章节ID、问题ID获取用户收藏记录
   */
  UserStoreRecord queryStoreRecord(String uid, String courseId, String chapterId, String questionId);

  /**
   * 根据用户ID、课程ID、章节ID获取用户收藏记录列表数量
   */
  Integer countStoreRecord(String uid, String courseId, String chapterId);

  /**
   * 插入新的收藏记录
   */
  Boolean insertStoreRecord(UserStoreRecord record);

  /**
   * 根据用户ID和课程ID获取收藏记录列表
   */
  List<UserStoreRecord> queryStoreRecordList(String uid, String courseId);

  /**
   * 根据条件列表获取用户收藏记录列表
   */
  /* List<UserStoreRecord> queryStoreRecordList(UserStoreRecord userStoreRecord); */
  List<UserStoreRecord> queryStoreRecordList(Map<String, Object> input);

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

  /**
   * 插入举报错题记录
   */
  Boolean insertRaiseWrongQues(RaiseWrongQues raise);

  /**
   * 修改错题状态
   */
  Boolean changeWrongQues(UserWrongRecord record);

  /**
   * 修改收藏状态
   */
  Boolean changeStoreQues(UserStoreRecord record);

  /**
   * 查询用户已购课程
   */
  List<ProductModel> queryBuyCourseByCond(Map<String, Object> params);

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

  /* 每日得分统计 xd_user_course_statistic_day */

  List<UserCourseStatisticDayModel> queryStatisticByDay(Map<String, Object> cond,
      Map<String, Object> output);

  Boolean insertStatisticByDay(UserCourseStatisticDayModel entity);

  Boolean updateStatisticByDay(Map<String, Object> cond, UserCourseStatisticDayModel entity);

  Page<TaskScoreModel> queryTaskScoreModel(IQueryParam param);

  void insertTaskScoreModel(TaskScoreModel model);

  void updateTaskScoreModel(TaskScoreModel model);

}
