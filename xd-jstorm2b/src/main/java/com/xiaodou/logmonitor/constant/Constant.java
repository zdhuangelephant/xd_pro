package com.xiaodou.logmonitor.constant;

import com.xiaodou.common.util.ConfigProp;

/**
 * 常量类
 * 
 * @author zhouhuan
 * 
 */
public class Constant {
  public static final String YES = "1";
  public static final String outin = "out_in";
  public static final String jmsgmessage = "jmsg_message";
  public static final Double OUTIN_THRESHOLD_PERCENT = Double.valueOf(ConfigProp.getParams("OUTIN_THRESHOLD_PERCENT"));
  public static final Double OVERTIME_THRESHOLD_PERCENT = Double.valueOf(ConfigProp.getParams("OVERTIME_THRESHOLD_PERCENT"));
  public static final Integer OVERTIME = Integer.valueOf(ConfigProp.getParams("OVERTIME"));  
}
