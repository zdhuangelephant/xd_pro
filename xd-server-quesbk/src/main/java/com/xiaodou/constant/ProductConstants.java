package com.xiaodou.constant;

/**
 * @name @see com.xiaodou.constant.ProductConstants.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月13日
 * @description 产品模块常量类
 * @version 1.0
 */
public class ProductConstants {

  public static final String PRODUCT_STATUS_IS_SHOW_STR = "1";

  public static final Integer PRODUCT_STATUS_IS_SHOW_INT = 1;

  public static final String PRODUCT_STATUS_IS_ORDER_STR = "2";

  public static final Integer PRODUCT_STATUS_IS_ORDER_INT = 2;

  /** RULE_TYPE_STUDY_COURSE_WARE 课件学习成绩 */
  public static final Short RULE_TYPE_STUDY_COURSE_WARE = 501;
  /** RULE_TYPE_ITEM_PRACTICE 节练习 */
  public static final Short RULE_TYPE_ITEM_PRACTICE = 901;
  /** RULE_TYPE_CHAPTER_PRACTICE 章总结 */
  public static final Short RULE_TYPE_CHAPTER_PRACTICE = 902;
  /** RULE_TYPE_FINAL_EXAM 期末测试 */
  public static final Short RULE_TYPE_FINAL_EXAM = 903;
  /** RULE_TYPE_CAT 机考 */
  public static final Short RULE_TYPE_CAT = 904;
  /** RULE_TYPE_STANDARD_MISSION 标准任务 */
  public static final Short RULE_TYPE_STANDARD_MISSION = 905;
  /** RULE_TYPE_LEAK_FILLING 查漏补缺 */
  public static final Short RULE_TYPE_LEAK_FILLING = 906;

  /** RULE_TYPE_STUDY_COURSE_WARE_LIMIT 课程学习成绩标准时长 */
  public static final String RULE_TYPE_STUDY_COURSE_WARE_LIMIT =
      "xd.quesbk.scorepoint.studycourseware.timelength";
}
