package com.xiaodou.dashboard.constant;

public interface Constant {

  /** PAGE_SIZE 默认分页数量 */
  public static final Integer PAGE_SIZE = 20;

  /**
   * 小逗通用前缀
   */
  public static final String XIAODOU = "xiaodou:";

  public static final String COMMON_DELIMITER = ":";

  /**
   * 项目健康仪表
   */
  public static final String DASHBOARD = XIAODOU + "dashboard:";

  /**
   * 短信15条校验
   */
  public static final String SMS_CODE = DASHBOARD + "sms:";

  /**
   * 报警描述
   */
  public static final String ALARM_DESC = DASHBOARD + "alarmDesc:";

  /**
   * 反馈15次校验
   */
  public static final String FEED_CODE = DASHBOARD + "feedcount:";

  /**
   * 增加反馈信息
   */
  public static final String ADD_FEED_BACK = DASHBOARD + "feedBack:";

  public static final int TIME_INTERVAL = 300000;

  /** ALARM_EXCEPTION_TYPE 异常报警 */
  public static final Integer ALARM_EXCEPTION_TYPE = 1;
  /** ALARM_MONITOR_TYPE 监控报警 */
  public static final Integer ALARM_MONITOR_TYPE = 2;
}
