package com.xiaodou.server.mapi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

public final class TimeUtil {

  /**
   * The UTC time zone (often referred to as GMT).
   */
  public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
  /**
   * Number of milliseconds in a standard second.
   * 
   * @since 2.1
   */
  public static final long MILLIS_PER_SECOND = 1000;
  /**
   * Number of milliseconds in a standard minute.
   * 
   * @since 2.1
   */
  public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
  /**
   * Number of milliseconds in a standard hour.
   * 
   * @since 2.1
   */
  public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
  /**
   * Number of milliseconds in a standard day.
   * 
   * @since 2.1
   */
  public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

  public static final int SECONDS_PER_DAY = 24 * 60 * 60;

  public static final int SECONDS_PER_HOUR = 60 * 60;

  public static final int SECONDS_PER_MINUTE = 60;



  public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
  public static final String yyyy_MM_dd = "yyyy-MM-dd";
  public static final String yyyy_MM_dd_00_00_00 = "yyyy-MM-dd 00:00:00";
  public static final String yyyyMMdd = "yyyyMMdd";



  public static Date parse_yyyy_MM_dd(String timeStr) {
    return parse(yyyy_MM_dd, timeStr);
  }

  public static Date parse_yyyy_MM_dd_00_00_00(String timeStr) {
    return parse(yyyy_MM_dd_00_00_00, timeStr);
  }

  public static Date parse_yyyy_MM_dd_HH_mm_ss(String timeStr) {
    return parse(yyyy_MM_dd_HH_mm_ss, timeStr);
  }

  public static String format_yyyy_MM_dd_HH_mm_ss(Date date) {
    return formate(yyyy_MM_dd_HH_mm_ss, date);
  }

  public static String format_yyyy_MM_dd_00_00_00(Date date) {
    return formate(yyyy_MM_dd_00_00_00, date);
  }

  public static String format_yyyy_MM_dd(Date date) {
    return formate(yyyy_MM_dd, date);
  }

  public static String format_yyyyMMdd(Date date) {
    return formate(yyyyMMdd, date);
  }


  public static Date parse(String format, String timeStr) {
    SimpleDateFormat formatSim = new SimpleDateFormat(format);
    Date d = null;
    try {
      d = formatSim.parse(timeStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return d;
  }

  public static String formate(String format, Date date) {
    SimpleDateFormat formatSim = new SimpleDateFormat(format);
    String s = formatSim.format(date);
    return s;
  }

  /**
   * 获得指定日期的前一天
   * 
   * @param dayStr
   * @return
   * @throws Exception
   */
  public static String getDayBefore(String timeFormat, String dayStr) {
    Calendar c = Calendar.getInstance();
    Date date = TimeUtil.parse(timeFormat, dayStr);
    c.setTime(date);
    int day = c.get(Calendar.DATE);
    c.set(Calendar.DATE, day - 1);
    String dayBefore = TimeUtil.formate(timeFormat, c.getTime());
    return dayBefore;
  }

  /**
   * 获得指定日期的前几天
   * 
   * @param dayStr
   * @return
   * @throws Exception
   */
  public static String getDayBefore(String timeFormat, String dayStr, Integer days) {
    Calendar c = Calendar.getInstance();
    Date date = TimeUtil.parse(timeFormat, dayStr);
    c.setTime(date);
    int day = c.get(Calendar.DATE);
    c.set(Calendar.DATE, day - days);
    String dayBefore = TimeUtil.formate(timeFormat, c.getTime());
    return dayBefore;
  }

  /**
   * 
   * 获取当前日期前几天
   * 
   * @return
   */
  public static String getCurrentTimeDayBefore(Integer days) {
    DateFormat dateFormat = new SimpleDateFormat(TimeUtil.yyyy_MM_dd_00_00_00);
    String dateFormated = dateFormat.format(new Date());
    String s = TimeUtil.getDayBefore(TimeUtil.yyyy_MM_dd_00_00_00, dateFormated, days);
    return s;
  }


  /**
   * 
   * @Title: getCurrDayBeforeZero
   * @Description: TODO(这里用一句话描述这个方法的作用)
   * @param @return 设定文件
   * @return Date 返回类型
   * @throws
   */



  /**
   * 
   * @Title: getDayBeforeZero
   * @Description: 获取前几天的零点时间[yyyy_MM_dd_00_00_00]
   * @param beforeDay
   * @return Date 返回类型
   * @throws
   */
  public static Date getDayBeforeZero(int beforeDay) {
    Date dayBefore = null;
    try {
      Calendar now = Calendar.getInstance();
      int day = now.get(Calendar.DATE);
      now.set(Calendar.DATE, day - beforeDay);
      SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_00_00_00);
      dayBefore = sdf.parse(sdf.format(now.getTime()));
    } catch (ParseException e) {}
    return dayBefore;
  }


  /**
   * 
   * @Title: getYesterdayZero
   * @Description: 获取前一天(昨天)的零点时间
   * @return Date 返回类型
   * @throws
   */
  public static Date getYesterdayZero() {
    return getDayBeforeZero(1);
  }


  /**
   * 获得指定日期的后一天
   * 
   * @param dayStr
   * @return
   */
  public static String getDayAfter(String timeFormat, String dayStr) {
    Calendar c = Calendar.getInstance();
    Date date = TimeUtil.parse(timeFormat, dayStr);
    c.setTime(date);
    int day = c.get(Calendar.DATE);
    c.set(Calendar.DATE, day + 1);
    String dayAfter = TimeUtil.formate(timeFormat, c.getTime());
    return dayAfter;
  }

  public static boolean isBetween(Date begin, Date curr, Date end) {
    // "2014-12-11 12:12:20".compareTo("2014-12-12 12:12:20") =-1
    if (begin.compareTo(curr) <= 0 && curr.compareTo(end) <= 0) {
      return true;
    }
    return false;
  }



  public static boolean isBeforeSomeHours(Date now, Date when, Double someHours) {
    Double someHoursMs = DateUtils.MILLIS_PER_HOUR * someHours;
    Long aftMs = when.getTime();
    Long befMs = now.getTime();
    return ((aftMs - befMs) > someHoursMs) ? true : false;
  }

  /**
   * 
   * 判断某一事件是否是几分钟之前
   * 
   * @param now
   * @param when
   * @param someMinutes
   * @return
   */
  public static boolean isBeforeSomeMinutes(Date now, Date when, Double someMinutes) {
    Double someHoursMs = DateUtils.MILLIS_PER_MINUTE * someMinutes;
    Long befMs = when.getTime();
    Long aftMs = now.getTime();
    return ((aftMs - befMs) > someHoursMs) ? true : false;
  }

  /**
   * 得到指定年月中第几个周日的long类型值
   * 
   * @param year 年
   * @param month 月
   * @param weekCount 第几周
   * @return
   */
  public static Long getDateBySun(int year, int month, int weekCount) {
    Long calTime = null;
    Calendar c_begin = new GregorianCalendar();
    Calendar c_end = new GregorianCalendar();
    c_begin.set(year, month - 1, 1); // Calendar的月从0-11
    c_end.set(year, month, 1); // 结束日期下滚一天是为了包含最后一天
    int count = 1;
    while (c_begin.before(c_end)) {
      if (count == weekCount) calTime = c_begin.getTime().getTime();
      if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) count++;
      c_begin.add(Calendar.DAY_OF_YEAR, 1);
    }
    return calTime;
  }
  
  
  /**
   * 获取当前时间之后24小时 hour
   * 
   * @param date
   * @return
   */
  public static Date getDateByHour(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, -24);
    date = calendar.getTime();
    return date;
  }
  
  public static Date getDateByDays(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -7);
    date = calendar.getTime();
    return date;
  }
  
  public static void main(String[] args) {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtil.getDateByHour(new Date())));
  }
  
}
