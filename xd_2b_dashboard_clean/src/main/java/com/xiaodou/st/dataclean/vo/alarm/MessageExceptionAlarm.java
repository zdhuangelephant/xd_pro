package com.xiaodou.st.dataclean.vo.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.st.dataclean.vo.alarm.MessageExceptionAlarm.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月21日
 * @description 消息消费异常报警类
 * @version 1.0
 */
public class MessageExceptionAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-data-clean";
  private String eventName = "message-consume-fail";
  private String messageInfo = "异步消息消费异常. [Tag:%s]";
  private String mailInfo = "异步消息消费异常. [Tag:%s][ErrorMsg:%s]";

  public MessageExceptionAlarm(String tag, String message) {
    this.messageInfo = String.format(this.messageInfo, tag, message);
    this.mailInfo = String.format(this.mailInfo, tag, message);
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
