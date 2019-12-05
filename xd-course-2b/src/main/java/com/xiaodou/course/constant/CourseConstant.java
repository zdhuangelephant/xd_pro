package com.xiaodou.course.constant;

import java.text.DecimalFormat;

public interface CourseConstant {
  public static int DEFAULT_REPEAT_TIME = 3;
  public static final String YES = "1";
  public static final String NO = "0";
  /** COMMON_MODULE 公共模块值 */
  public static final String COMMON_MODULE = "-1";
  /** D_FORMAT double format */
  public static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");
  public static final DecimalFormat DD_FORMAT = new DecimalFormat("0.0000");
  /** ORDER_STATUS_NORMAL 订单正常购买状态 */
  public static final String ORDER_STATUS_NORMAL = "1";
  /** ORDER_STATUS_TRY 2：试用状态 */
  public static final String ORDER_STATUS_TRY = "2";
  /** ORDER_STATUS_VALID 1：有效 */
  public static final String ORDER_STATUS_VALID = "1";
  /** ORDER_STATUS_IN_VAIN 2：无效 */
  public static final String ORDER_STATUS_IN_VAIN = "2";

  /** TRADE_TYPE_OUTCOME 交易类型: 未支付订单 */
  public static final String TRADE_TYPE_ORDER = "-1";
  /** TRADE_TYPE_INCOME 交易类型: 收入 */
  public static final String TRADE_TYPE_INCOME = "0";
  /** TRADE_TYPE_OUTCOME 交易类型: 支出 */
  public static final String TRADE_TYPE_OUTCOME = "1";

  /* 考期类型 */
  /* UP_EXAM_DATE 0:上半年 */
  public static final String UP_EXAM_DATE = "0";
  /* DOWN_EXAM_DATE 1:下半年 */
  public static final String DOWN_EXAM_DATE = "1";
  /* ALL_EXAM_DATE 2:全年 */
  public static final String ALL_EXAM_DATE = "2";
  // 4月第2、第3个周六日<->4,3,0
  // 10月第2、第3个周六日<_>10,3,0
  /* UP_DATE_EXAM_DATE 426:4月第2个周六 */
  public static final Integer UP_DATE_EXAM_DATE = 426;
  /* DOWN_DATE_EXAM_DATE 1026:10月第2个周六 */
  public static final Integer DOWN_DATE_EXAM_DATE = 1026;
  /* UP_MONTH_EXAM_DATE 04:4月 */
  public static final String UP_MONTH_EXAM_DATE = "04";
  /* DOWN_MONTH_EXAM_DATE 10:10月 */
  public static final String DOWN_MONTH_EXAM_DATE = "10";
  /* RECENT_EXAM_DATE_STATUS 1:近期 */
  public static final String RECENT_EXAM_DATE_STATUS = "1";
  /* OTHER_RECENT_EXAM_DATE_STATUS 2:其它考期 */
  public static final String OTHER_RECENT_EXAM_DATE_STATUS = "2";

  /** RESOURCE_TYPE_QUESBK 资源类型 章节课程 */
  public static final Long RESOURCE_TYPE_COURSE = 1l;
  /** RESOURCE_TYPE_VIDEO 资源类型 视频 */
  public static final Long RESOURCE_TYPE_VIDEO = 2l;
  /** RESOURCE_TYPE_DOC 资源类型 文档 */
  public static final Long RESOURCE_TYPE_DOC = 3l;
  /** RESOURCE_TYPE_POINT 资源类型 知识点 */
  public static final Long RESOURCE_TYPE_POINT = 4l;
  /** RESOURCE_TYPE_CHAPTER 资源类型 章 */
  public static final Long RESOURCE_TYPE_CHAPTER = 5l;
  /** RESOURCE_TYPE_ITEM 资源类型 节 */
  public static final Long RESOURCE_TYPE_ITEM = 6l;
  /** RESOURCE_TYPE_ITEM 资源类型 音频 */
  public static final Long RESOURCE_TYPE_AUDIO = 7l;

  /** RESOURCE_TYPE_MICRO 资源类型 微课 */
  public static final Long RESOURCE_TYPE_MICRO = 8l;

  /* 章节列表状态 */
  /* CHAPTER_ITEM_STATUS_LOCK 0 未解锁 */
  public static final Short CHAPTER_ITEM_STATUS_LOCK = 0;
  /* CHAPTER_ITEM_STATUS_UNLOCK 1 已解锁 */
  public static final Short CHAPTER_ITEM_STATUS_UNLOCK = 1;
  /* CHAPTER_ITEM_STATUS_COMPLETE 2 已完成 */
  public static final Short CHAPTER_ITEM_STATUS_COMPLETE = 2;

  /* 1修炼2闯关3废弃状态(闯关中途退出)4做题计时 */
  public static final String LEARN_TYPE_XL = "1";
  public static final String LEARN_TYPE_CHALLENGE = "2";
  public static final String LEARN_TYPE_ABANDON = "3";
  public static final String LEARN_TYPE_QUESBK = "4";

  public static final String LEARN_COST_START = "1";
  public static final String LEARN_COST_FINISH = "2";

  /* 是否有效(1:有效2：无效) */
  /* IS_EXP 1:有效 */
  public static final String IS_VALID = "1";
  /* IS_NOT_EXP 2：无效 */
  public static final String IS_NOT_VALID = "2";
  /* NOT_HAS_LEARNED 0 未学习 */
  public static final String NOT_HAS_LEARNED = "0";
  /* HAS_LEARNED 1 已学习 */
  public static final String HAS_LEARNED = "1";

  /* 产品有效 */
  /* PRODUCT_SHOW_STATUS 1 有效 */
  public static final String PRODUCT_SHOW_STATUS = "1";
  /*********************************************
   * order
   *********************************************/
  public static final String PRODUCT_TYPE = "01";// 自考课程产品

  /** BUY_COURSE_INIT 购买课程初始化 */
  public static final Integer BUY_COURSE_INIT = 1;
  /** BUY_COURSE_FINISH 购买课程完成 */
  public static final Integer BUY_COURSE_FINISH = 2;

  /* 学习记录统计页面 */
  /** LEARN_RECORD_STATISTIC_DATA:1：统计页面数据 */
  public final static int LEARN_RECORD_STATISTIC_DATA = 1;

  /* LEARN_RECORD_DETAIL_DATA:2：详情列表数据 */
  public final static int LEARN_RECORD_DETAIL_DATA = 2;

  /* COMMON_MODULE_COURSE 普通课程课程module_course */
  public final static String COMMON_MODULE_COURSE = "0";
  public static final Integer BUSINESS_TYPE_ZKJ = 11020001;

  /********************************************* sign *********************************************/
  /** CANT_SING 无法打卡 */
  public final static Integer CANT_SING = 0;
  /** WAIT_SING 未打卡 */
  public final static Integer WAIT_SING = 1;
  /** HAS_SING 已打卡 */
  public final static Integer HAS_SING = 2;


  /** COMMON_MAJOR_ID 通用专业 */
  public static final String COMMON_MAJOR_ID = "-1";

  /* 报名状态（0：后台报名1：业务系统报名成功2,已经购买该课程） */
  public static final Short APPLY_SUCCESS = 0;
  public static final Short BUSSINESS_APPLY_SUCCESS = 1;
  public static final Short BUSSINESS_APPLY_ALREADY = 2;

  /**
   * @description 学习记录 类型 <br>
   *              11随机PK答题,12随机PK解析<br>
   *              21节闯关答题,22节闯关解析<br>
   *              31修炼， <br>
   *              41错题， <br>
   *              51每日一练答题,52每日一练解析，<br>
   *              61期末考试答题,62期末考试解析，<br>
   *              71查漏补缺答题,72查漏补缺解析<br>
   *              81章练习答题<br>
   *              91章总结答题,92章总结解析<br>
   */
  public static final String LEARN_RECORD_TYPE_PK_EXAM = "11";
  public static final String LEARN_RECORD_TYPE_PK_ANALYZE = "12";
  public static final String LEARN_RECORD_TYPE_ITEM_BT_EXAM = "21";
  public static final String LEARN_RECORD_TYPE_ITEM_BT_ANALYZE = "22";
  public static final String LEARN_RECORD_TYPE_TRAINING = "31";
  public static final String LEARN_RECORD_TYPE_WRONGQUES = "41";
  public static final String LEARN_RECORD_TYPE_DAILY = "51";
  public static final String LEARN_RECORD_TYPE_DAILY_ANALYZE = "52";
  public static final String LEARN_RECORD_TYPE_FINAL = "61";
  public static final String LEARN_RECORD_TYPE_FINAL_ANALYZE = "62";
  public static final String LEARN_RECORD_TYPE_SUPPLEMENT = "71";
  public static final String LEARN_RECORD_TYPE_SUPPLEMENT_ANALYZE = "72";

  public static final String LEARN_RECORD_TYPE_CHAPTER_EX_EXAM = "81";
  public static final String LEARN_RECORD_TYPE_CHAPTER_BT_EXAM = "91";
  public static final String LEARN_RECORD_TYPE_CHAPTER_BT_ANALYZE = "92";

  /** LEARN_RECORD_TYPE_TASK_TYPE_VIDEO 视频 */
  public static final String LEARN_RECORD_TYPE_TASK_TYPE_VIDEO = "101";
  /** LEARN_RECORD_TYPE_TASK_TYPE_AUDIO 音频 */
  public static final String LEARN_RECORD_TYPE_TASK_TYPE_AUDIO = "102";

  // @Deprecated
  public static final String LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO = "103";

  /**********************************************
   * cache
   *************************************************/
  /** PRODUCT 产品缓存前缀 */
  public final static String PRODUCT = "xd:course:product:%s";

  /** PRODUCT_LIST 产品列表缓存前缀 */
  public final static String PRODUCT_LIST = "xd:course:productlist:%s:%s";

  /** PRODUCT_ITEM_LIST 产品条目列表缓存前缀 */
  public final static String PRODUCT_ITEM_LIST = "xd:course:productitemlist:%s";

  /** PRODUCT_ITEM_CHAPTER_LIST 产品条目章列表缓存前缀 */
  public final static String PRODUCT_ITEM_CHAPTER_LIST = "xd:course:productitem:chapter:list:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节列表缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_LIST = "xd:course:productitem:item:list:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节列表缓存前缀 */
  public final static String PRODUCT_FINAL_EXAM_LIST = "xd:course:productfinalexamlist:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节信息缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_INFO = "xd:course:productitem:iteminfo:%s";


  /** PRODUCT_LIST_TASK_DELAY 产品列表刷新调度任务执行周期 */
  public final static String PRODUCT_LIST_TASK_DELAY = "xd.course.product.list.task.delay";

  /** MODULE 北京 */
  public static final String MODULE = "2";
  /** MODULE_NAME */
  public static final String MODULE_NAME = "北京市";

  public static final String PAY_PRODUCT_LINE = "2";

  /** STANDARD_BREAK_THROUGH_THRESHOLD 闯关标准阀值(单位seconds) */
  public static final Long STANDARD_BREAK_THROUGH_THRESHOLD = 3 * 60L;

  // 所在层级(从0级开始，最好不要超过3级) level=0表示章， level=1表示节，level=2表示节下面的课件、视频。
  public static final short LEVEL_CHAPTER = 0;
  public static final short LEVEL_item = 1;
  public static final short LEVEL_RESOURCE = 2;


  // 课件学习状态 1:已完成 0:未完成
  public static final Integer FINISHED = 1;
  public static final Integer UNFINISHED = 0;

  /** FINAL_EXAM_CHAPTER_ID 期末测试章ID */
  public static final String FINAL_EXAM_CHAPTER_ID = "-9";
  
  /** MONGO_CACHE_TIME_DAYS 默认mongo缓存时长 100day */
  public static final Integer MONGO_CACHE_TIME_DAYS = 100;
}
