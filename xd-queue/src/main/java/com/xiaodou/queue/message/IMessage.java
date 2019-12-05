package com.xiaodou.queue.message;

import java.util.Date;
import java.util.UUID;

/**
 * @name @see com.xiaodou.queue.client.model.IMessage.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月23日
 * @description Message抽象接口
 * @version 1.0
 */
public interface IMessage {

  /**
   * 消息ID:消息唯一标识
   * 
   * @return message_id
   */
  public UUID getMessageId();

  /**
   * 消息名:消息路由/分类凭证
   * 
   * @return message_name
   */
  public String getMessageName();

  /**
   * 消息体:实际用于消费的消息体
   * 
   * @return message_body
   */
  public String getMessageBodyJson();

  /**
   * 发送时间
   * 
   * @return send_time
   */
  public Date getSendTime();

  /**
   * 接收时间
   * 
   * @return receive_time
   */
  public Date getReceiveTime();

}
