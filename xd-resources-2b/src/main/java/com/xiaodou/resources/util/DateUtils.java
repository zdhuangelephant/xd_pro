package com.xiaodou.resources.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
  public static String getDateForDays(int years, int months, int days) {
    Calendar cal = Calendar.getInstance();
    if (years != 0) cal.add(Calendar.YEAR, years);
    if (months != 0) cal.add(Calendar.MONTH, months);
    if (days != 0) cal.add(Calendar.DATE, days);
    String newDate = DateUtil.SDF_YMD.format(cal.getTime());
    return newDate;
  }

  public static void main(String[] args) {
    System.out.println(getDateForDays(0, 0, -1));
    System.out.println(9 / 7);
    System.out.println(9 % 7);
  }

  /**
   * 计算差多少天
   * 
   * @param beginTime
   * @param endTime
   * @return
   * @throws ParseException
   */
  public static int getDiffDays(String beginTime, String endTime) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Long beginL = sdf.parse(beginTime).getTime();
    Long endL = sdf.parse(endTime).getTime();
    long diff = endL - beginL;
    // 计算差多少天
    long day = diff / DateUtil.MILLIS_PER_DAY;
    return (int) day;
  }

  /**
   * 计算差多少周
   * 
   * @param beginTime
   * @param endTime
   * @return
   * @throws ParseException
   */
  public static int getDiffWeeks(String beginTime, String endTime) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Long beginL = sdf.parse(beginTime).getTime();
    Long endL = sdf.parse(endTime).getTime();
    long diff = endL - beginL;
    // 计算差多少天
    long day = diff / DateUtil.MILLIS_PER_DAY;
    long week = day / 7;
    if ((day % 7) > 0) {
      week += 1;
    }
    return (int) week;
  }
}
