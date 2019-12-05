package com.xiaodou.jmsg.client.vo;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.vo.alarm.ExamDetailAlarm.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月3日
 * @description 消息接口报警类
 * @version 1.0
 */
public class JmsgClientAlarm extends AlarmEntityImpl {

  private String eventModule = "xiaodou-jmsg-client";
  private String eventName = "jmsg-client-fail";
  private String messageInfo = "消息发送失败. %s";
  private String mailInfo = "消息发送失败. %s";

  public JmsgClientAlarm(String message) {
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
