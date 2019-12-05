package com.xiaodou.vo.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.vo.alarm.MqMessageExceptionAlarm.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 异步消息消费异常报警类
 * @version 1.0
 */
public class MqMessageExceptionAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-quesbk";
  private String eventName = "mq-message-exception";
  private String messageInfo = "异步消息消费异常. %s";
  private String mailInfo = "异步消息消费异常. %s";

  public MqMessageExceptionAlarm(String message) {
    this.messageInfo = String.format(this.messageInfo, message);
    this.mailInfo = String.format(this.mailInfo, message);
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
