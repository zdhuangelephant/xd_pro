package com.xiaodou.mission;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import freemarker.template.SimpleDate;

/**
 * @name @see com.xiaodou.mission.DateTest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 日期单测类
 * @version 1.0
 */
public class DateTest {
  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

  public static void main(String[] args) {
    // compare(0);
    // compare(1);
    // compare(10);
    // compare(24);
    // compare(25);
    // compare(-1);
    // compare(-10);
    // compare(-23);
    // compare(-24);
    // compare(-25);
    // System.out.println(DateUtil.SDF_FULL.format(new Date()));
    // System.out.println(DateUtil.SDF_YMD.format(new Date()));
    // System.out.println(DateUtil.SDF_MD.format(new Date()));
    System.out.println(UUID.randomUUID().toString());
  }

  public static void compare(int i) {
    long time = System.currentTimeMillis() + i * 3600000;
    Date targetDate = new SimpleDate(new Timestamp(time)).getAsDate();
    System.out.println(String.format("%s小时后compare 今天 : ", i)
        + format.format(targetDate).compareTo(format.format(new Date())));
  }
}
