package com.xiaodou.oms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

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

  /**
   * 查看date是否在start和end之间
   * @param start
   * @param end
   * @param date
   * @return 
   */
  public static boolean isBetweenTime(Date start, Date end, Date date){
    return (date.after(start) && date.before(end))?true:false;

  }
  
  /**
   * 获取几分钟之后的时间值
   * @param date
   * @param minues
   * @return
   */
  public static Date getAfterMinues(Date date,int minues){
    Calendar cal=Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, minues);
    
    return cal.getTime();
  }

  /**
   * 获取endTime-startTime的分钟差值
   * @param beginTime
   * @param endTime
   * @return
   */
  public static int getDiffMinues(Date beginTime, Date endTime){
    Calendar begin=Calendar.getInstance();
    begin.setTime(beginTime);
    begin.set(Calendar.MILLISECOND, 0);
    Calendar end=Calendar.getInstance();
    end.setTime(endTime);
    end.set(Calendar.MILLISECOND, 0);
    Long beginL = begin.getTimeInMillis();
    Long endL = end.getTimeInMillis();
    long diff = endL - beginL;
    // 计算差多少天
    long day = diff / MILLIS_PER_DAY;
    // 计算差多少小时
    long hour = diff % MILLIS_PER_DAY / MILLIS_PER_HOUR;
    // 计算差多少分钟
    long minues = diff % MILLIS_PER_DAY % MILLIS_PER_HOUR / MILLIS_PER_MINUTE;
    return (int) (day * 24 + hour * 60 +minues);
  }

  public static int getDiffHours(String beginTime, String endTime) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
    Long beginL = sdf.parse(beginTime).getTime();
    Long endL = sdf.parse(endTime).getTime();
    long diff = endL - beginL;
    // 计算差多少天
    long day = diff / MILLIS_PER_DAY;
    // 计算差多少小时
    long hour = diff % MILLIS_PER_DAY / MILLIS_PER_HOUR;
    return (int) (day * 24 + hour);
  }

  public static int getDiffDays(String beginTime, String endTime) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Long beginL = sdf.parse(beginTime).getTime();
    Long endL = sdf.parse(endTime).getTime();
    long diff = endL - beginL;
    // 计算差多少天
    long day = diff / MILLIS_PER_DAY;
    return (int) day;
  }
  
  public static long getTimeBeforeDays(int days) {
    Calendar cal=Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-days);
    
    return cal.getTimeInMillis();
    
  }
  
}
