package com.xiaodou.oms.vo.agent;

/**
 * Date: 2014/12/2
 * Time: 15:45
 *
 * @author Tian.Dong
 */
public class MessagePojo {
  //tag
  private String tag;
  //消息名称
  private String messageName;
  //消息体
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
