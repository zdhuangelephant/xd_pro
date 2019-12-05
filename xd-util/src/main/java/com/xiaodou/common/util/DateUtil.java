package com.xiaodou.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

/**
 * DateUtil
 * 
 * @Title: DateUtil
 * @Desription 时间处理工具类
 * @author Guanguo.Gao
 * @date 2014年12月29日 下午3:08:21
 */
public class DateUtil {

  /**
   * 一天，毫秒最小单位
   */
  public static final int DAY_MILLISECOND = 24 * 60 * 60 * 1000;

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

  public static final DateFormat SDF_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static final DateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");

  public static final DateFormat SDF_MD = new SimpleDateFormat("MM-dd");

  public static final ThreadLocal<DateFormat> DF_FULL = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
  };

  public static final ThreadLocal<DateFormat> DF_YMD = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd");
    }
  };

  public static final ThreadLocal<DateFormat> DF_MD = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("MM-dd");
    }
  };

  /**
   * 查看date是否在start和end之间
   * 
   * @param start
   * @param end
   * @param date
   * @return
   */
  public static boolean isBetweenTime(Date start, Date end, Date date) {
    return (date.after(start) && date.before(end)) ? true : false;

  }

  /**
   * 获取几分钟之后的时间值
   * 
   * @param date
   * @param minues
   * @return
   */
  public static Date getAfterMinues(Date date, int minues) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, minues);

    return cal.getTime();
  }

  /**
   * 获取endTime-startTime的分钟差值
   * 
   * @param beginTime
   * @param endTime
   * @return
   */
  public static int getDiffMinues(Date beginTime, Date endTime) {
    Calendar begin = Calendar.getInstance();
    begin.setTime(beginTime);
    begin.set(Calendar.MILLISECOND, 0);
    Calendar end = Calendar.getInstance();
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
    return (int) (day * 24 + hour * 60 + minues);
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
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - days);

    return cal.getTimeInMillis();

  }

  /**
   * 用于话题部分回显时间 如：1分钟前，2小时前，3天前，4月前，5年前
   * 
   * @return
   * @throws ParseException
   */
  public static String relativeDateFormat(Timestamp time) {
    if (null == time) return StringUtils.EMPTY;
    // 参数时间
    Calendar cal = Calendar.getInstance();
    // 当前时间
    Calendar cal1 = Calendar.getInstance();
    Date date = new Date();
    cal.setTime(time);
    cal1.setTime(date);
    long delta = date.getTime() - cal.getTime().getTime();
    long[] itimes =
        new long[] {7 * MILLIS_PER_DAY, MILLIS_PER_DAY, MILLIS_PER_HOUR, MILLIS_PER_MINUTE,
            MILLIS_PER_SECOND};
    String[] sunits = new String[] {"月前", "天前", "小时前", "分钟前", "秒前"};
    if (cal.get(Calendar.YEAR) != cal1.get(Calendar.YEAR)) {
      return SDF_YMD.format(cal.getTime());
    }
    if (delta < MILLIS_PER_SECOND) {
      return "刚刚";
    }
    for (int i = 0, len = itimes.length; i < len; i++) {
      long itemp = itimes[i];
      if (delta < itemp) {
        continue;
      }
      long temp2 = delta / itemp;
      if (temp2 > 0) {
        if (i == 0) {
          return SDF_MD.format(cal.getTime());
        } else {
          return temp2 + sunits[i];
        }
      }
    }
    return time.toString().split(" ")[0];
  }

  /**
   * 计算两个时间的见夜数
   * 
   * @param inDate
   * @param outDate
   * @return
   */
  public static long getNightlyDay(Date inDate, Date outDate) {
    // 去除时间的时分秒
    Date inDateOfDay = DateUtils.truncate(inDate, Calendar.DAY_OF_MONTH);
    Date outDateOfDay = DateUtils.truncate(outDate, Calendar.DAY_OF_MONTH);
    long millis = Math.abs(outDateOfDay.getTime() - inDateOfDay.getTime());
    return millis / (1000 * 60 * 60 * 24);
  }

  /**
   * 去除时间的时分秒，保留年月日
   * 
   * @param datetime
   * @return
   */
  public static Date truncateDayOfMonth(Date datetime) {
    return DateUtils.truncate(datetime, Calendar.DAY_OF_MONTH);
  }

  /**
   * 去除时间的时分秒，保留年月日
   * 
   * @param datetime
   * @return
   */
  public static Date truncateDayOfMonth(long timestamp) {
    return DateUtils.truncate(new Date(timestamp), Calendar.DAY_OF_MONTH);
  }

  /**
   * 
   * 格式化日期，日期格式给入参格式
   * 
   * @Title: formatDate
   * @Description: TODO
   * @param format
   * @return
   */
  public static String formatDate(String format) {
    DateFormat dateFormat = new SimpleDateFormat(format);
    Date date = new Date();
    String currentDate = dateFormat.format(date);
    return currentDate;
  }

  /**
   * formatDate
   * 
   * @Title: formatDate
   * @Description: 格式化指定的日期
   * @param inputTime
   * @param format yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String formatDate(Timestamp inputTime, String format) {
    DateFormat df = new SimpleDateFormat(format);
    return df.format(inputTime);
  }

  /**
   * getDaysBetween
   * 
   * @Title: getDaysBetween
   * @Description: 计算日期差
   * @param beforeTime
   * @param afterTime
   * @return
   */
  public static long getDaysBetween(Timestamp beforeTime, Timestamp afterTime) {
    long beforeMils = getStartTimeOfDay(beforeTime);
    long afterMils = getStartTimeOfDay(afterTime);
    long mils = afterMils - beforeMils;
    return Math.abs(mils / DAY_MILLISECOND);
  }

  /**
   * getStartTimeOfDay
   * 
   * @Title: getStartTimeOfDay
   * @Description: 获得当天的开始
   * @param curTime
   * @return
   */
  public static Long getStartTimeOfDay(Timestamp curTime) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(curTime);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime().getTime();
  }

  /**
   * getLastTimeOfDay
   * 
   * @Title: getStartTimeOfDay
   * @Description: 获得当天的结束
   * @param curTime
   * @return
   */
  public static Long getLastTimeOfDay(Timestamp curTime) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(curTime);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime().getTime();
  }

  /**
   * 判断传入的时间（年月日）对比当前时间（年月日）的大小 如果传入时间小于当前时间返回true 如果传入时间大于等于当前时间返回false isbeforeNow
   * 
   * @Title: isbeforeNow
   * @Description: TODO
   * @return
   */
  public static boolean isbeforeNow(Date day) {
    // 1,处理参数为null的情况
    if (day == null) {
      return true;
    }
    // 2,保留时间的年月日
    long nowTiems = truncateDayOfMonth(System.currentTimeMillis()).getTime();
    long dayTimes = truncateDayOfMonth(day.getTime()).getTime();
    return dayTimes < nowTiems;
  }

  /**
   * convertToTimestamp
   * 
   * @Title: convertToTimestamp
   * @Description: 转换成timestamp
   * @param timestr
   * @return
   */
  public static Timestamp convertToTimestamp(String timestr) {
    try {
      return Timestamp.valueOf(timestr);
    } catch (Exception e) {
      return null;
    }
  }


  /**
   * calSecondsBetweenTimestamp
   * 
   * @Title: calSecondsBetweenTimestamp
   * @Description: 计算两个时间时间的s数
   * @param before
   * @param after
   * @return
   */
  public static long calSecondsBetweenTimestamp(Timestamp before, Timestamp after) {
    long beforeMils = before.getTime();
    long afterMils = after.getTime();
    long betweenSeconds = Math.abs(afterMils - beforeMils) / 1000;
    return betweenSeconds;
  }

  /**
   * 获得当天0点时间
   * 
   * @return
   */
  public static long getTimesmorning(int day) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTimeInMillis();
  }

  /**
   * 获得当天24点时间
   * 
   * @return
   */
  public static long getTimesnight(int day) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
    cal.set(Calendar.HOUR_OF_DAY, 24);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTimeInMillis() - 1000;
  }

  /**
   * 获得本周一0点时间
   * 
   * @return
   */
  public static long getTimesWeekmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTimeInMillis();
  }

  /**
   * 获得本周日24点时间
   * 
   * @return
   */
  public static long getTimesWeeknight() {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTime().getTime() + (7 * 24 * 60 * 60 * 1000);
  }

  /**
   * 获得本月第一天0点时间
   * 
   * @return
   */
  public static long getTimesMonthmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    return cal.getTimeInMillis();
  }

  /**
   * 获得本月最后一天24点时间
   * 
   * @return
   */
  public static long getTimesMonthnight() {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 24);
    return cal.getTimeInMillis();
  }

}
