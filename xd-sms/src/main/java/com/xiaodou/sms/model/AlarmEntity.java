package com.xiaodou.sms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlarmEntity extends AlarmEntityImpl {

  private String eventModule = "xd-sms";
  private String eventName;
  private String messageInfo;
  private String mailInfo;


  public static enum FailTypeEnum {
    SEND_MS_FAIL("send-ms-fail", "发送消息失败."), 
    SEND_SMS_FAIL("send-sms-fail", "发送短信失败. [ErrorMsg:%s]");
    private String eventName;
    private String messageInfo;

    private FailTypeEnum(String eventName, String messageInfo) {
      this.eventName = eventName;
      this.messageInfo = messageInfo;
    }

    public String getEventName() {
      return eventName;
    }

    public void setEventName(String eventName) {
      this.eventName = eventName;
    }

    public String getMessageInfo() {
      return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
      this.messageInfo = messageInfo;
    }
  }

  public AlarmEntity(FailTypeEnum failEnum, String message) {
    this.eventName = failEnum.getEventName();
    this.messageInfo = String.format(failEnum.getMessageInfo(), message);
    this.mailInfo = String.format(failEnum.getMessageInfo(), message);
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
