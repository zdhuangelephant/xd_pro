package com.xiaodou.constant;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @see com.xiaodou.constant.QuesBaseConstant
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月7日
 * @description 常量
 * @version 1.0
 */
public interface QuesBaseConstant {

  public static final String COMMON_MAJOR_ID = "-1";

  /** D_FORMAT double format */
  public static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");

  /** RESOURCE_TYPE_QUESBASE 资源类型-题库 */
  public static final Integer RESOURCE_TYPE_QUESBASE = 1;

  /** RESOURCE_TYPE_VIDEO 资源类型-视频 */
  public static final Integer RESOURCE_TYPE_VIDEO = 2;

  /** RESOURCE_TYPE_DOCUMENT 资源类型-文档 */
  public static final Integer RESOURCE_TYPE_DOCUMENT = 3;

  /** RESOURCE_TYPE_KEYWORD 资源类型-知识点 */
  public static final Integer RESOURCE_TYPE_KEYWORD = 4;

  /** RESOURCE_TYPE_CHAPTER 资源类型-章 */
  public static final Integer RESOURCE_TYPE_CHAPTER = 5;

  /** RESOURCE_TYPE_ITEM 资源类型-节 */
  public static final Integer RESOURCE_TYPE_ITEM = 6;

  /** QUES_TYPE_RECORD_WRONG 记录错题类型:客观题 */
  public static final Short QUES_TYPE_RECORD_WRONG = 0;

  /** QUES_TYPE_SIMPLE_CHOICE 问题类型:单选 */
  public static final String QUES_TYPE_SIMPLE_CHOICE = "xd.quesbk.ques.type.simple.choice";

  /** QUES_TYPE_MULTIPLE_CHOICE 问题类型:多选 */
  public static final String QUES_TYPE_MULTIPLE_CHOICE = "xd.quesbk.ques.type.multiple.choice";

  /** QUES_TYPE_SUBJECTIVE 问题类型:主观 */
  public static final String QUES_TYPE_SUBJECTIVE = "xd.quesbk.ques.type.subjective";

  /** QUES_TYPE_DIRECTIONS 问题类型:说明 */
  public static final String QUES_TYPE_DIRECTIONS = "xd.quesbk.ques.type.directions";


  /** YES 肯定状态 收费/可以练习等 */
  public static final String YES = "1";

  /** NO 否定状态 不收费/不可以练习等 */
  public static final String NO = "0";

  /** 个人收藏问题列表分隔符 */
  public static final String STORE_QUES_SPLIT = "#";

  /** 年-月-日 格式化 yyyy-MM-dd */
  public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  /** 问题类型拼接前缀-拼接APP返回值 */
  public static final String QUES_TYPE = "qt";

  /** 问题类型拼接分隔符-拼接APP返回值 */
  public static final String QUES_TYPE_SPLIT = "#";

  /** HOMEWORK_DEFAULT_QUESNUM 课后练习默认问题数 */
  public static final String HOMEWORK_DEFAULT_QUESNUM = "homework";

  /** ITEM_DEFAULT_QUESNUM 节练习默认问题数 */
  public static final String ITEM_DEFAULT_QUESNUM = "item";

  /** CHAPTER_DEFAULT_QUESNUM 章练习默认问题数 */
  public static final String CHAPTER_DEFAULT_QUESNUM = "chapter";

  /** APTITUDE_DEFAULT_QUESNUM 智能练习默认问题数 */
  public static final String APTITUDE_DEFAULT_QUESNUM = "aptitude";

  /** BREAKTHROUGH_DEFAULT_QUESNUM 闯关练习节默认问题数 */
  public static final String BREAKTHROUGH_ITEM_DEFAULT_QUESNUM = "item_breakthrough";

  /** BREAKTHROUGH_DEFAULT_QUESNUM 闯关练习章总结默认问题数 */
  public static final String BREAKTHROUGH_CHAPTER_DEFAULT_QUESNUM = "chapter_breakthrough";

  /** CHALLENGE_DEFAULT_QUESNUM 挑战练习默认问题数 */
  public static final String CHALLENGE_DEFAULT_QUESNUM = "challenge";

  /** FINAL_EXAM_DEFAULT_QUESNUM 期末测试问题数 */
  public static final String FINAL_EXAM_DEFAULT_QUESNUM = "final_exam";

  /** DAILY_PRACTICE_DEFAULT_QUESNUM 每日练习默认问题数 */
  public static final String DAILY_PRACTICE_DEFAULT_QUESNUM = "daily_practice";

  /** LEAK_FILLING_DEFAULT_QUESNUM 查漏补缺默认问题数 */
  public static final String LEAK_FILLING_DEFAULT_QUESNUM = "leak_filling";

  /** QUESNUM_LOWER 问题数量下限模板 */
  public static final String QUESNUM_LOWER = "xd.quesbk.quesnum.%s.lower";

  /** QUESNUM_UPPER 问题数量上限模板 */
  public static final String QUESNUM_UPPER = "xd.quesbk.quesnum.%s.upper";

  /** 问题类型 缓存key **/
  public final static String QUES_TYPE_PREFIX = "xd:quesbk:ques:type";

  /** QUES_TYPE_TASK_DELAY 问题类型调度任务执行周期 */
  public final static String QUES_TYPE_TASK_DELAY = "xd.quesbk.ques.type.task.delay";

  /** PRODUCT 产品缓存前缀 */
  public final static String PRODUCT = "xd:quesbk:product:%s";

  /** PRODUCT_LIST 产品列表缓存前缀 */
  public final static String PRODUCT_LIST = "xd:quesbk:productlist:%s:%s";

  /** PRODUCT_ITEM_LIST 产品条目列表缓存前缀 */
  public final static String PRODUCT_ITEM_LIST = "xd:quesbk:productitemlist:%s";

  /** PRODUCT_ITEM_CHAPTER_LIST 产品条目章列表缓存前缀 */
  public final static String PRODUCT_ITEM_CHAPTER_LIST = "xd:quesbk:productitem:chapter:list:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目章信息缓存前缀 */
  public final static String PRODUCT_ITEM_CHAPTER_INFO = "xd:quesbk:productitem:chapterinfo:%s:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节信息缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_INFO = "xd:quesbk:productitem:iteminfo:%s:%s:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节列表缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_LIST = "xd:quesbk:productitem:item:list:%s:%s";

  /** PRODUCT_LIST_TASK_DELAY 产品列表刷新调度任务执行周期 */
  public final static String PRODUCT_LIST_TASK_DELAY = "xd.quesbk.product.list.task.delay";

  /** QUES_TYPE_DESC_TEMPLATE 题型描述模板 */
  public final static String QUES_TYPE_DESC_TEMPLATE = "每题%s分,共%s题。";

  /** QUES_CHALLENGE_FRIEND 好友挑战类型 */
  public final static Short QUES_CHALLENGE_FRIEND = 1;

  /** QUES_CHALLENGE_RANDOM 随机挑战类型 */
  public final static Short QUES_CHALLENGE_RANDOM = 2;

  /** QUES_CHALLENGE_STATUS_DAIYINGZHAN 挑战记录状态 待应战 */
  public final static Short QUES_CHALLENGE_STATUS_DAIYINGZHAN = 1;

  /** QUES_CHALLENGE_STATUS_YIKAISHI 挑战记录状态 已开始 */
  public final static Short QUES_CHALLENGE_STATUS_YIKAISHI = 2;

  /** QUES_CHALLENGE_STATUS_WEIWANCHENG 挑战记录状态 未完成 */
  public final static Short QUES_CHALLENGE_STATUS_WEIWANCHENG = 3;

  /** QUES_CHALLENGE_STATUS_YIJIESHU 挑战记录状态 已结束 */
  public final static Short QUES_CHALLENGE_STATUS_YIJIESHU = 4;

  /** QUES_CHALLENGE_RESULT_UNKNOWN 挑战结果 未知 */
  public final static Short QUES_CHALLENGE_RESULT_UNKNOWN = 0;

  /** QUES_CHALLENGE_RESULT_SUCCESS 挑战结果 成功 */
  public final static Short QUES_CHALLENGE_RESULT_SUCCESS = 1;

  /** QUES_CHALLENGE_RESULT_FAIL 挑战结果 失败 */
  public final static Short QUES_CHALLENGE_RESULT_FAIL = 2;

  /** QUES_CHALLENGE_RESULT_DRAW 挑战结果 平局 */
  public final static Short QUES_CHALLENGE_RESULT_DRAW = 3;

  /** QUES_CHALLENGE_RESULT_DRAW_WINNER 平局默认winner */
  public final static String QUES_CHALLENGE_RESULT_DRAW_WINNER = "-1";

  /** QUES_USER_CHAPTER_STATUS_UNTHROUGH 用户章节闯关状态 未通过 */
  public final static Integer QUES_USER_CHAPTER_STATUS_UNTHROUGH = -1;

  /** QUES_USER_CHAPTER_STATUS_UNLOCK 用户章节闯关状态 未解锁 */
  public final static Integer QUES_USER_CHAPTER_STATUS_LOCKED = 0;

  /** QUES_USER_CHAPTER_STATUS_UNLOCK 用户章节闯关状态 已解锁 */
  public final static Integer QUES_USER_CHAPTER_STATUS_UNLOCK = 1;

  /** QUES_USER_CHAPTER_STATUS_HASTHROUGHED 用户章节闯关状态 已通过 */
  public final static Integer QUES_USER_CHAPTER_STATUS_HASTHROUGHED = 2;

  /** QUES_USER_CHAPTER_STATUS_HASTHROUGHED 用户章节闯关状态 已完成 */
  public final static Integer QUES_USER_CHAPTER_STATUS_HASFINISHED = 3;

  /** QUES_WRONG_RECORD_STATUS_UNCONTROL 错题状态 未掌握 */
  public final static String QUES_WRONG_RECORD_STATUS_UNCONTROL = "1";

  /** QUES_WRONG_RECORD_STATUS_NEEDEXAM 错题状态 待强化 */
  public final static String QUES_WRONG_RECORD_STATUS_NEEDEXAM = "2";

  /** QUES_WRONG_RECORD_STATUS_RESOLVED 错题状态 已消灭 */
  public final static String QUES_WRONG_RECORD_STATUS_RESOLVED = "3";

  /** QUES_WRONG_RECORD_STATUS_REMOVED 错题状态 已移除 */
  public final static String QUES_WRONG_RECORD_STATUS_REMOVED = "4";

  /** QUES_STORE_RECORD_STATUS_GOODQUES 收藏状态 好题(默认) */
  public final static String QUES_STORE_RECORD_STATUS_GOODQUES = "1";

  /** QUES_STORE_RECORD_STATUS_UNKNOWQUES 收藏状态 我不会的 */
  public final static String QUES_STORE_RECORD_STATUS_UNKNOWQUES = "2";

  /** QUES_STORE_RECORD_STATUS_NEEDMEMERYQUES 收藏状态 需要记忆 */
  public final static String QUES_STORE_RECORD_STATUS_NEEDMEMERYQUES = "3";

  /** QUES_STORE_RECORD_STATUS_CANCELQUES 收藏状态 取消收藏 */
  public final static String QUES_STORE_RECORD_STATUS_CANCELQUES = "4";

  /** raise_wrong_ques_type_wrongly_written 举报错题 错题类型 有错别字 */
  public final static String raise_wrong_ques_type_wrongly_written = "1";
  /** raise_wrong_ques_type_wrongly_content 举报错题 错题类型 内容有错 */
  public final static String raise_wrong_ques_type_wrongly_content = "2";
  /** raise_wrong_ques_type_wrongly_answer 举报错题 错题类型 答案有错 */
  public final static String raise_wrong_ques_type_wrongly_answer = "3";
  /** raise_wrong_ques_type_wrongly_orther 举报错题 错题类型 其它 */
  public final static String raise_wrong_ques_type_wrongly_orther = "4";

  /** ASYNC_MESSAGE_ADDCREDIT 增加积分消息名 */
  public final static String ASYNC_MESSAGE_ADDCREDIT = "%s_addCredit";

  /** QUES_DOMAIN_OPERATION_CREATE 数据操作类型-无动作 */
  public final static Integer QUES_DOMAIN_OPERATION_NOTHING = 0;

  /** QUES_DOMAIN_OPERATION_CREATE 数据操作类型-新增 */
  public final static Integer QUES_DOMAIN_OPERATION_CREATE = 1;

  /** QUES_DOMAIN_OPERATION_MODIFY 数据操作类型-修改 */
  public final static Integer QUES_DOMAIN_OPERATION_MODIFY = 2;

  /** QUES_DOMAIN_OPERATION_DELETE 数据操作类型-删除 */
  public final static Integer QUES_DOMAIN_OPERATION_DELETE = -1;

  /** RED_BONUS_STATUS_INIT 红包状态 未领取 */
  public final static Short RED_BONUS_STATUS_INIT = 0;

  /** RED_BONUS_STATUS_GET 红包状态 已领取 */
  public final static Short RED_BONUS_STATUS_GET = 1;

  /** LEARN_RECORD_STATISTIC_DATA:1：统计页面数据 */
  public final static int LEARN_RECORD_STATISTIC_DATA = 1;

  /* LEARN_RECORD_DETAIL_DATA:2：详情列表数据 */
  public final static int LEARN_RECORD_DETAIL_DATA = 2;

  /* NEWBIE_COURSE_CODE 新手课程课程码值 */
  public final static String NEWBIE_COURSE_CODE = "00000";

  /** IMPORT_USER_TYPE 导入用户类型 */
  public final static String IMPORT_USER_TYPE = "2";

  /** FACE_RECOGNITION_ALARM 人脸识别-报警 */
  public final static String FACE_RECOGNITION_ALARM = "1";
  /** FACE_RECOGNITION_NOTALARM 人脸识别-不报警 */
  public final static String FACE_RECOGNITION_NOTALARM = "0";

  /* module类型 */
  public static final String MODELE_STR = "2";
  /** ADD_SCORE_STATUS_CANT 加分状态:不能加分 */
  public static final Integer ADD_SCORE_STATUS_CANT = 0;
  /** ADD_SCORE_STATUS_CAN 加分状态:可以加分 */
  public static final Integer ADD_SCORE_STATUS_CAN = 1;
  /** CREDIT_RED_BONUS_TYPE 积分红包类型 */
  public static final String CREDIT_RED_BONUS_TYPE = "Credit_Red_Bonus";

  /** RULE_ITEM_TYPE_PRODUCT 规则类型:产品级 */
  public static final Short RULE_ITEM_TYPE_PRODUCT = 0;
  /** RULE_ITEM_TYPE_CHAPTER 规则类型:章级 */
  public static final Short RULE_ITEM_TYPE_CHAPTER = 1;
  /** RULE_ITEM_TYPE_ITEM 规则类型:节级 */
  public static final Short RULE_ITEM_TYPE_ITEM = 2;
  /** RULE_ITEM_TYPE_KEYPOINT 规则类型:考点级 */
  public static final Short RULE_ITEM_TYPE_KEYPOINT = 3;

  /** MONGO_CACHE_TIME_DAYS 默认mongo缓存时长 100day */
  public static final Integer MONGO_CACHE_TIME_DAYS = 100;
}
