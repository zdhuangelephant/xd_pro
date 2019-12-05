package com.xiaodou.jmsg.entity;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;


/**
 * Created by zyp on 14-8-1.
 */
public class JMSGPojo extends BaseValidatorPojo {

  // tag
  @NotEmpty
  private String tag;
  // 消息名称
  @NotEmpty
  private String messageName;
  // 消息体
  @NotEmpty
  private String message;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
