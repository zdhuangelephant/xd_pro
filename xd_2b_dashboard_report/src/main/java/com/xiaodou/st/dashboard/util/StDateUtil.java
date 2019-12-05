package com.xiaodou.st.dashboard.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import org.apache.commons.lang.math.NumberUtils;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;

public class StDateUtil extends com.xiaodou.common.util.DateUtil {

  public static final DateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
  private static String intValidate = "[0-9]*";


  /**
   * 
   * @description 验证数字
   * @author 李德洪
   * @Date 2017年10月31日
   * @param num
   * @return
   */
  public static boolean validateInt(String num) {
    if (StringUtils.isBlank(num)) return false;
    Pattern p = Pattern.compile(intValidate);
    Matcher m = p.matcher(num);
    return m.matches();
  }
  
public static void main(String[] args) {
  
  System.out.println(NumberUtils.isNumber("5.96"));
  //System.out.println(isNumeric("123.34"));
}
  /**
   * 获取入参时间的前一天
   * 
   * @param date
   * @return
   */
  public static Date getNextDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    date = calendar.getTime();
    return date;
  }

  /**
   * 获取入参时间的 间隔天数的时间
   * 
   * @param date
   * @param interval
   * @return
   */
  public static Date getDayByInterval(Date date, Integer interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, interval);
    date = calendar.getTime();
    return date;
  }

  public static DateFormat getSYMDFormat() {
    return new SimpleDateFormat("yyyy年MM月dd日");
  }

  /*
   * 判断当前年
   */
  public static int getYearOfDate(Date date) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int yearOfDate = calendar.get(Calendar.YEAR);
    return yearOfDate;
  }

  /*
   * 判断当前日期是第几月
   */
  public static int getMonthOfDate(Date date) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int monthOfDate = calendar.get(Calendar.MONTH) + 1;
    return monthOfDate;
  }

  /*
   * 根据日期得到当月第几周
   */
  public static int getWeekOfMonth(Date date) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
    return weekOfMonth;
  }

  /**
   * 判断当前日期是星期几
   */
  public static String getWeekOfDate(Date date) {
    String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
    // String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    return weekDaysCode[intWeek];
  }

  /*
   * c 要填充的字符 length 填充后字符串的总长度 content 要格式化的字符串 格式化字符串，左对齐
   */
  public static String flushLeft(String c, long length, String content) {
    String str = "";
    String cs = "";
    if (content.length() > length) {
      str = content;
    } else {
      for (int i = 0; i < length - content.length(); i++) {
        cs = c + cs;
      }
    }
    str = cs + content;
    return str;
  }

  /**
   * 当天距离规定日期的天数
   * 
   * @param year
   * @param month
   * @param day
   * @return
   */
  public static int intervalDays(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    // cal.set(year, month - 1, day, 0, 0, 0);
    // cal.set(2016, 3, 23, 0, 0, 0);
    Calendar now = Calendar.getInstance();
    now.set(Calendar.HOUR_OF_DAY, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    long intervalMilli = cal.getTimeInMillis() - now.getTimeInMillis();
    int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
    return xcts;
  }

  public static int getFirstWeekdayOfMonth(int year, int month) {// 求本月第一个周日
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(Calendar.SATURDAY);// 周日第一天

    c.set(year, month - 1, 5);
    return c.get(Calendar.DAY_OF_WEEK);
  }

  public static Date toDate(String dateStr) {// 将字符串格式化成日期
    Date d = null;
    SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日");
    try {
      formater.setLenient(false);
      d = formater.parse(dateStr);
    } catch (Exception e) {
      d = null;
    }
    return d;
  }

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

  /**
   * 获取参数日期日期的年、月、天前/后的值
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
    String newDate = DateUtil.SDF_MD.format(cal.getTime());
    return newDate;
  }

  /**
   * 获取endTime-startTime的天数差值
   * 
   * @param beginTime
   * @param endTime
   * @return
   */
  public static int getDiffDays(Date beginTime, Date endTime) {
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
    return (int) day;
  }

  /**
   * 判断当前时间是否在有效期内
   * 
   * @param t1
   * @param t2
   * @return
   */
  public static boolean ifIsExp(Timestamp t1, Timestamp t2) {
    Timestamp nowTime = new Timestamp(System.currentTimeMillis());
    if (nowTime.after(t1) && nowTime.before(t2) || nowTime.equals(t1) || nowTime.equals(t2)) {
      return true;
    }
    return false;
  }

  /**
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月7日
   * @param dValue
   * @return
   */
  public static String getLongStringValue(Double dValue) {
    try {
      return Long.toString(new Double(Math.ceil(dValue)).longValue());
    } catch (Exception e) {
      return "0";
    }
  }

  /**
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月7日
   * @param dValue
   * @return
   */
  public static Double getCeilDouble(Double dValue) {
    try {
      return Math.ceil(dValue);
    } catch (Exception e) {
      return 0d;
    }
  }

  /**
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月7日
   * @param dValue
   * @return
   */
  public static Long getCeilLong(Double dValue) {
    try {
      return (long) Math.ceil(dValue);
    } catch (Exception e) {
      return 0l;
    }
  }
}
