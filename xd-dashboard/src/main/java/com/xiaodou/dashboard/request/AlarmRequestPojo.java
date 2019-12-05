package com.xiaodou.dashboard.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see com.xiaodou.dashboard.request.AlarmRequestPojo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警请求类
 * @version 1.0
 */
public class AlarmRequestPojo extends BaseValidatorPojo {

  /** eventModule 所属模块 */
  @NotEmpty
  private String eventModule;

  /** eventName 事件名称 */
  @NotEmpty
  private String eventName;

  /** messageInfo 短信内容 */
  @NotEmpty
  private String messageInfo;

  /** mailInfo 邮件内容 */
  @NotEmpty
  private String mailInfo;

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

}
