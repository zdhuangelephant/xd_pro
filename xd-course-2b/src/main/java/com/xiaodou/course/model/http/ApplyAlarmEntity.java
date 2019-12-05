package com.xiaodou.course.model.http;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.http.ApplyRequest.ApplyRequestDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyAlarmEntity extends AlarmEntityImpl {

  private String eventModule = "xd-course";
  private String eventName = "import-produce-fail";
  private String messageInfo = "导入课程报警-[%s].";
  private String mailInfo = "导入课程报警-[%s]. %s";

  public ApplyAlarmEntity(String message, ApplyRequestDTO pojo) {
    this.messageInfo = String.format(this.messageInfo, message);
    this.mailInfo = String.format(this.mailInfo, message, FastJsonUtil.toJson(pojo));
  }

  public String getEventModule() {
    return eventModule;
  }

  public void setEventModule(String eventModule) {
    this.eventModule = eventModule;
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

  public String getMailInfo() {
    return mailInfo;
  }

  public void setMailInfo(String mailInfo) {
    this.mailInfo = mailInfo;
  }

  public IAlarmEntity getLoggerEntity() {
    return this;
  }

}
