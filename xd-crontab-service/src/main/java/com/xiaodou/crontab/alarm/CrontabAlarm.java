package com.xiaodou.crontab.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.crontab.alarm.CrontabAlarm.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月20日
 * @description 定时任务报警类
 * @version 1.0
 */
public class CrontabAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-crontab";
  private String eventName = "crontab-fail";
  private String messageInfo = "定時任务异常. [%s]-[%s]";
  private String mailInfo = "定時任务异常. [%s]-[%s]";

  public CrontabAlarm(String url, String result) {
    this.messageInfo = String.format(this.messageInfo, url, result);
    this.mailInfo = String.format(this.mailInfo, url, result);
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
