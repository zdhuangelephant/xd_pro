package com.xiaodou.jmsg.server.vo;

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
public class JmsgAlarm extends AlarmEntityImpl {

  private String eventModule = "xiaodou-jmsg-server";
  private String eventName = "jmsg-server-fail";
  private String messageInfo = "消息处理系统异常. %s";
  private String mailInfo = "消息处理系统异常. %s";

  public JmsgAlarm(String message) {
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
