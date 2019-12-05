package com.xiaodou.server.mapi.constant;

public interface SelfTaughtConstant {

  /** TRUE 逻辑:真 */
  public static final Integer TRUE = 1;
  /** FALSE 逻辑:假 */
  public static final Integer FALSE = 0;

  /** QUES_CHALLENGE_FRIEND 好友挑战类型 */
  public final static Short QUES_CHALLENGE_FRIEND = 1;

  /** QUES_CHALLENGE_RANDOM 随机挑战类型 */
  public final static Short QUES_CHALLENGE_RANDOM = 2;

  /* orderStatus 购买状态(1:正常购买状态，2：试用状态) */
  /* ORDERSTATUS_NORMAL 1:正常购买状态 */
  public final static String ORDERSTATUS_NORMAL = "1";
  /* ORDERSTATUS_TAIAL 2：试用状态 */
  public final static String ORDERSTATUS_TRIAL = "2";
  public static final Integer BUSINESS_TYPE_ZKJ = 11020001;
  /* 榜单类型 1 人气(按被赞次数) 2 学霸（按积分数）3 星星（按星星数量） */
  public final static String CREDIT_RANK = "2";

  public final static String STAR_RANK = "3";

  public final static String MAN = "1";
  public final static String WOMAN = "2";

  public final static Integer CHAPTER_SCORE = 50;

  public final static Integer FINAL_SCORE = 80;

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
  
  /**MODULE 北京*/
  public static final String MODULE = "2";
}
