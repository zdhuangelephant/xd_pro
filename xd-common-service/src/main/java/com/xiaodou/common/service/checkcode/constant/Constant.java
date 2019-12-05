package com.xiaodou.common.service.checkcode.constant;

/**
 * 
 * 常量定义
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class Constant {

  /**
   * 小逗通用前缀
   */
  public static final String XIAODOU = "xiaodou:";

  public static final String COMMON_DELIMITER = ":";

  /**
   * 验证码通用前缀
   */
  public static final String CHECK_CODE = XIAODOU + "user:checkcode:";

  /**
   * 短信15条校验
   */
  public static final String SMS_CODE = CHECK_CODE + "sms:";

  /**
   * 反馈15次校验
   */
  public static final String FEED_CODE = CHECK_CODE + "feedcount:";

}
