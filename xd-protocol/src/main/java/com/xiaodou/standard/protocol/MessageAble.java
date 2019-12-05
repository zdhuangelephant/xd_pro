package com.xiaodou.standard.protocol;

/**
 * @name @see com.xiaodou.standard.protocol.MessageAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 传递性
 * @version 1.0
 */
public interface MessageAble {

  /**
   * 唯一消息名
   * 
   * @return 唯一消息名
   */
  public String uniqueMessageName();
  
  /**
   * 设置 唯一消息名
   * 
   * @param uniqueMessageName 唯一消息名
   */
  public void setUniqueMessageName(String uniqueMessageName);

  /**
   * 消息体内容
   * 
   * @return 消息体内容
   */
  public String messageContent();
  
  /**
   * 设置 消息体内容
   * 
   * @param messageContent 消息体内容
   */
  public void setMessageContent(String messageContent);

  /**
   * 来源地
   * 
   * @return 源
   */
  public TargetSocket from();

  /**
   * 目的地
   * 
   * @return 目标
   */
  public TargetSocket to();
}
