package com.xiaodou.crontmonitor.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.crontmonitor.alarm.CrontMonitorAlarm.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 定时监控报警类
 * @version 1.0
 */
public class CrontMonitorAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-cront-monitor";
  private String eventName = "monitor-fail";
  private String messageInfo = "定時监控异常. [%s]-[%s]";
  private String mailInfo = "定時监控异常. [%s]-[%s]";

  public CrontMonitorAlarm(String name, String url) {
    this.messageInfo = String.format(this.messageInfo, name, url);
    this.mailInfo = String.format(this.mailInfo, name, url);
  }

  @Override
  public String getEventModule() {
    return eventModule;
  }

  @Override
  public String getEventName() {
    return eventName;
  }

  @Override
  public String getMessageInfo() {
    return messageInfo;
  }

  @Override
  public String getMailInfo() {
    return mailInfo;
  }

  @Override
  public IAlarmEntity getLoggerEntity() {
    return this;
  }

}
