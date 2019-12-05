package com.xiaodou.resources.constant.product;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public interface CourseConstant {

  /** D_FORMAT double format */
  public static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");
  /** ORDER_STATUS_NORMAL 订单正常购买状态 */
  public static final String ORDER_STATUS_NORMAL = "1";
  /** ORDER_STATUS_TRY 2：试用状态 */
  public static final String ORDER_STATUS_TRY = "2";
  /** ORDER_STATUS_VALID 1：有效 */
  public static final String ORDER_STATUS_VALID = "1";
  /** ORDER_STATUS_IN_VAIN 2：无效 */
  public static final String ORDER_STATUS_IN_VAIN = "2";

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


  /* 1修炼2闯关3废弃状态(闯关中途退出)4做题计时 */
  public static final String LEARN_TYPE_XL = "1";
  public static final String LEARN_TYPE_CHALLENGE = "2";
  public static final String LEARN_TYPE_ABANDON = "3";
  public static final String LEARN_TYPE_QUESBK = "4";

  public static final String LEARN_COST_START = "1";
  public static final String LEARN_COST_FINISH = "2";

  /* 是否有效(1:有效2：无效) */
  /* IS_EXP 1:有效 */
  public static final String IS_EXP = "1";
  /* IS_NOT_EXP 2：无效 */
  public static final String IS_NOT_EXP = "2";
  /* NOT_HAS_LEARNED 0 未学习 */
  public static final String NOT_HAS_LEARNED = "0";
  /* HAS_LEARNED 1 已学习 */
  public static final String HAS_LEARNED = "1";

  /* 产品有效 */
  /* PRODUCT_SHOW_STATUS 1 有效 */
  public static final String PRODUCT_SHOW_STATUS = "1";
  /********************************************* order *********************************************/
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

  /* NEWBIE_MODULE_COURSE 新手课程类型 */
  public final static String NEWBIE_MODULE_COURSE = "1";

  /* NEWBIE_COURSE_CODE 新手课程课程码值 */
  public final static String NEWBIE_COURSE_CODE = "00000";
  /* COMMON_MODULE_COURSE 普通课程课程module_course */
  public final static String COMMON_MODULE_COURSE = "0";

  /* RECOMMEND_COURSE_CODE 推荐课程 */
  public final static String RECOMMEND_COURSE_CODE = "0";
  /* MODULE_SLIDE_HOME 首页轮播图 */
  public final static String MODULE_SLIDE_HOME = "1";
  /* MODULE_SLIDE_ADD 课程添加页轮播图 */
  public final static String MODULE_SLIDE_PRODUCT = "3";

  public final static Integer PAGESIZE = 5;

  public static final DateFormat SDF_MD = new SimpleDateFormat("MM月dd日");
  public static final String NOWTIMESTRING = "今天";
  
  public static final String NODEADLINE = "无截至时间";
  
  public static final String NOJOINRESULT = "无参与结果";
  
  public static final String ISPRAISE = "1";
  
  public static final String ISNOTPRAISE = "0";
}
