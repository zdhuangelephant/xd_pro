package com.xiaodou.server.mapi.vo.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name RemoteInvokeExceptionAlarm 
 * CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年3月15日
 * @description 异步消息消费异常报警类 
 * @version 1.0
 */
public class RemoteInvokeExceptionAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-mapi";
  private String eventName = "call-service-fail";
  private String messageInfo = "异步消息消费异常. %s";
  private String mailInfo = "异步消息消费异常. %s";

  public RemoteInvokeExceptionAlarm(String message) {
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