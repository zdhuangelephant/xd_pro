package com.xiaodou.server.pay.vo.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name @see com.xiaodou.vo.alarm.ExamDetailAlarm.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月19日
 * @description 支付接口报警类
 * @version 1.0
 */
public class PayAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-pay";
  private String eventName = "pay-fail";
  private String messageInfo = "支付接口异常. %s";
  private String mailInfo = "支付接口异常. %s";

  public PayAlarm(String message) {
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
