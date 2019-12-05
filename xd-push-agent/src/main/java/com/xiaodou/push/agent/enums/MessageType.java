package com.xiaodou.push.agent.enums;

/**
 * @name @see cpm.xiaodou.push.agent.enums.MessageType.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 消息类型
 * @version 1.0
 */
public enum MessageType {
  /** ALL 通知+消息 */
  ALL("0"), 
  /** NOTICE 通知 */
  NOTICE("2"), 
  /** MESSAGE 消息 */
  MESSAGE("3");
  private String messageType;
  private MessageType(String messageType){
    this.messageType = messageType;
  }
  public String getMessageType() {
    return messageType;
  }
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }
  
}
