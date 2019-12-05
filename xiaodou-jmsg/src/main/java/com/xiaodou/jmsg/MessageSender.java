package com.xiaodou.jmsg;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

public interface MessageSender {

  /**
   * 发送消息
   * 
   * @param message 包装后的消息
   */
  <T extends AbstractMessagePojo> void send(T message);

}
