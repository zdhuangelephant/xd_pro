package com.xiaodou.userCenter.util;

import java.util.regex.Pattern;


public class VerifyUtil {


  /**
   * 正则表达式：验证手机号
   */
  public static final String REGEX_MOBILE = "^(1)\\d{10}$";

  /**
   * 正则表达式：验证邮箱
   */
  public static final String REGEX_EMAIL =
      "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";

  /**
   * 正则表达式：验证汉字
   */
  public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

  /**
   * 正则表达式：验证身份证
   */
  public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

  /**
   * 正则表达式：验证URL
   */
  public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

  /**
   * 正则表达式：验证IP地址
   */
  public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

  /**
   * 6-20字母和数字组成
   */
  public static final String NUM_STR ="^[0-9A-Za-z]{6,20}$";
  /**
   * 校验手机号
   * 
   * @param mobile
   * @return 校验通过返回true，否则返回false
   */
  public static boolean isMobile(String mobile) {
    return Pattern.matches(REGEX_MOBILE, mobile);
  }

  /**
   * 校验邮箱
   * 
   * @param email
   * @return 校验通过返回true，否则返回false
   */
  public static boolean isEmail(String email) {
    return Pattern.matches(REGEX_EMAIL, email);
  }

  /**
   * 校验汉字
   * 
   * @param chinese
   * @return 校验通过返回true，否则返回false
   */
  public static boolean isChinese(String chinese) {
    return Pattern.matches(REGEX_CHINESE, chinese);
  }

  /**
   * 校验身份证
   * 
   * @param idCard
   * @return 校验通过返回true，否则返回false
   */
  public static boolean isIDCard(String idCard) {
    return Pattern.matches(REGEX_ID_CARD, idCard);
  }

  /**
   * 校验URL
   * 
   * @param url
   * @return 校验通过返回true，否则返回false
   */
  public static boolean isUrl(String url) {
    return Pattern.matches(REGEX_URL, url);
  }

  /**
   * 校验IP地址
   * 
   * @param ipAddr
   * @return
   */
  public static boolean isIPAddr(String ipAddr) {
    return Pattern.matches(REGEX_IP_ADDR, ipAddr);
  }
  
  public static boolean isNUMSTR(String numStr){
    return Pattern.matches(NUM_STR, numStr);
  }
  
  public static void main(String[] args) {
    System.out.println(isEmail("1119737151@qq.com"));
    System.out.println(isNUMSTR("123456"));
  }
  
}
