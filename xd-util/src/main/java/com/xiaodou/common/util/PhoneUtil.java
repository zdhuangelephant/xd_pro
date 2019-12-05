package com.xiaodou.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @name @see com.xiaodou.common.util.PhoneUtil.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月10日
 * @description 手机号验证类
 * @version 1.0
 */
public class PhoneUtil {

  private static String phoneValidate = "^[1][3-8]+\\d{9}";
  

  /**
   * 验证手机号格式是否正确
   * 
   * @param phoneNum 待验证手机号
   * @return 验证结果
   */
  public static boolean validatePhone(String phoneNum) {
    if (StringUtils.isBlank(phoneNum) || phoneNum.trim().length() != 11) return false;
    Pattern p = Pattern.compile(phoneValidate);
    Matcher m = p.matcher(phoneNum);
    return m.matches();
  }
  
  /**
   * 验证验数字格式是否正确
   * 
   * @param number 待验证数字
   * @param amount 数字位数
   * @return 验证结果
   */
  public static boolean validateNumber(String number ,int amount) {
    if (StringUtils.isBlank(number) || number.trim().length() != amount) return false;
    String numberValidate = "^\\d{"+amount+"}$";
    Pattern p = Pattern.compile(numberValidate);
    Matcher m = p.matcher(number);
    return m.matches();
  }
}
