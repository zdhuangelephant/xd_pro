package com.xiaodou.common.util;

/**
 * @name @see com.xiaodou.common.util.MathUtil.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月20日
 * @description 数字操作util类
 * @version 1.0
 */
public class MathUtil {

  /**
   * 获取字符串类型整型
   * 
   * @param dValue 双精度浮点类型
   * @return 字符串整型
   */
  public static String getIntStringValue(Double dValue) {
    try {
      return Long.toString(Math.round(dValue));
    } catch (Exception e) {
      return "0";
    }
  }

  /**
   * 获取字符串类型整型
   * 
   * @param sValue 双精度浮点类型
   * @return 字符串整型
   */
  public static String getIntStringValue(String sValue) {
    try {
      return Long.toString(Math.round(Double.valueOf(sValue)));
    } catch (Exception e) {
      return "0";
    }
  }

}
