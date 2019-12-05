package com.xiaodou.course.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.xiaodou.course.constant.CourseConstant;

public class DateUtil extends com.xiaodou.common.util.DateUtil {

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
   * 根据用户第几次考试，获取改次考试的考期时间值
   * 
   * @param frequency 次数
   * @return
   * @throws ParseException
   */
  public static Date getRecentExamStr(int frequency) {
    // 获取当前日期
    CaculateExamDate caculateDate = new CaculateExamDate();
    // 获取 -1 考期 = exam-1
    Calendar preExam = caculateDate.getPreExam();
    // 获取 0 考期 = exam0
    Calendar currExam = caculateDate.getCurrExam();
    // 获取 1 考期 = exam+1
    Calendar nextExam = caculateDate.getNextExam();
    int varYear = frequency / 2, chooseExam = frequency % 2;
    if (chooseExam == -1) {
      preExam.add(Calendar.YEAR, -varYear);
      return preExam.getTime();
    }
    if (chooseExam == 0 && frequency >= 0) {
      currExam.add(Calendar.YEAR, varYear);
      return currExam.getTime();
    }
    if (chooseExam == 0 && frequency < 0) {
      currExam.add(Calendar.YEAR, -varYear);
      return currExam.getTime();
    }
    if (chooseExam == 1) {
      nextExam.add(Calendar.YEAR, varYear);
      return nextExam.getTime();
    }
    return null;
  }

  private static class CaculateExamDate {
    public CaculateExamDate() {}

    public Calendar getNextExam() {
      Calendar cal = new GregorianCalendar();
      cal.setTime(currentDate);
      cal.add(Calendar.YEAR, 1);
      if (iCurrentDate >= CourseConstant.UP_DATE_EXAM_DATE
          && iCurrentDate < CourseConstant.DOWN_DATE_EXAM_DATE) {
        cal.set(Calendar.MONTH, Calendar.APRIL);
      } else {
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
      }
      cal.set(Calendar.WEEK_OF_MONTH, 2);
      cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
      return cal;
    }

    public Calendar getPreExam() {
      Calendar cal = new GregorianCalendar();
      cal.setTime(currentDate);
      if (iCurrentDate < CourseConstant.UP_DATE_EXAM_DATE) cal.add(Calendar.YEAR, -1);
      if (iCurrentDate >= CourseConstant.UP_DATE_EXAM_DATE
          && iCurrentDate < CourseConstant.DOWN_DATE_EXAM_DATE) {
        cal.set(Calendar.MONTH, Calendar.APRIL);
      } else {
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
      }
      cal.set(Calendar.WEEK_OF_MONTH, 2);
      cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
      return cal;
    }

    public Calendar getCurrExam() {
      Calendar cal = new GregorianCalendar();
      cal.setTime(currentDate);
      if (iCurrentDate > CourseConstant.DOWN_DATE_EXAM_DATE) cal.add(Calendar.YEAR, 1);
      if (iCurrentDate >= CourseConstant.UP_DATE_EXAM_DATE
          && iCurrentDate < CourseConstant.DOWN_DATE_EXAM_DATE) {
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
      } else {
        cal.set(Calendar.MONTH, Calendar.APRIL);
      }
      cal.set(Calendar.WEEK_OF_MONTH, 2);
      cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
      return cal;
    }

    private Date currentDate = new Date();
    private int iCurrentDate = 0;
    {
      Calendar cal = new GregorianCalendar();
      cal.setTime(currentDate);
      iCurrentDate =
          (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.WEEK_OF_MONTH) * 10
              + cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
  }

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
  public static boolean ifIsValid(Timestamp t1, Timestamp t2) {
    if (null == t1 || null == t2) return false;
    Timestamp nowTime = new Timestamp(System.currentTimeMillis());
    if (nowTime.after(t1) && nowTime.before(t2) || nowTime.equals(t1) || nowTime.equals(t2)) {
      return true;
    }
    return false;
  }
  
  /**
   * 获取字符串类型整型
   * 
   * @param dValue 双精度浮点类型
   * @return 字符串整型
   */
  public static String getLongStringValue(Double dValue) {
    try {
      return Long.toString(new Double(Math.ceil(dValue)).longValue());
    } catch (Exception e) {
      return "0";
    }
  }
  
  public static String SecNumbersToMmSs(Long seconds) {
    if(null == seconds || seconds==0L) return "00:00";
    Long minutes = seconds / 60;
    Long secs = seconds % 60;
    return String.format("%s:%s", minutes, secs);
  }
  
  public static void main(String[] args) {
    System.out.println(SecNumbersToMmSs(135l));
  }
}
