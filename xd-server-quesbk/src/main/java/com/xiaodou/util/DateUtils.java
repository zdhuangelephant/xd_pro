package com.xiaodou.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.xiaodou.common.util.DateUtil;

/**
 * 
 * @name DateUtils
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月17日
 * @description 题库日期处理
 * @version 1.0
 */
public class DateUtils {
  /**
   * 获取当前日期的年、月、天前/后的值
   * 
   * @param days 几天(负数为前几天，正数为后几天)
   * @return
   */
//  public static String getDateForDays(int years, int months, int days) {
//    Calendar cal = Calendar.getInstance();
//    if (years != 0) cal.add(Calendar.YEAR, years);
//    if (months != 0) cal.add(Calendar.MONTH, months);
//    if (days != 0) cal.add(Calendar.DATE, days);
//    String newDate = DateUtil.SDF_YMD.format(cal.getTime());
//    return newDate;
//  }

  /**
   * 获取参数日期的年、月、天前/后的值
   * 
   * @param days 几天(负数为前几天，正数为后几天)
   * @return
   */
  public static String getDateForDateAndDays(Date date, int years, int months, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (years != 0) cal.add(Calendar.YEAR, years);
    if (months != 0) cal.add(Calendar.MONTH, months);
    if (days != 0) cal.add(Calendar.DATE, days);
    String newDate = DateUtil.SDF_YMD.format(cal.getTime());
    return newDate;
  }

  public static boolean ifIsExp(Timestamp t1, Timestamp t2) {
    Timestamp nowTime = new Timestamp(System.currentTimeMillis());
    if (nowTime.after(t1) && nowTime.before(t2) || nowTime.equals(t1) || nowTime.equals(t2)) {
      return true;
    }
    return false;
  }

}
