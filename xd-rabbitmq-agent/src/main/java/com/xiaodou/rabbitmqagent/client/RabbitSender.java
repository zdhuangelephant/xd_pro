package com.xiaodou.rabbitmqagent.client;

import java.io.IOException;

/**
 * 向rabbit-mq中发送数据
 * 
 * Date: 2014/4/23 Time: 18:20
 * 
 * @author Tian.Dong
 */
public class RabbitSender extends EndPoint {

  private long initTime;

  public long getInitTime() {
    return initTime;
  }

  @Override
  public void init() throws IOException {
    super.init();
    initTime = System.currentTimeMillis();
  }

  /**
   * 向消息队列发送数据
   * 
   * @param message
   * @throws IOException
   */
  public void sendMessage(String message) throws IOException {
    channel.basicPublish(this.getExchangeName(), this.getEndPointName(), null, message.getBytes());
  }
}
