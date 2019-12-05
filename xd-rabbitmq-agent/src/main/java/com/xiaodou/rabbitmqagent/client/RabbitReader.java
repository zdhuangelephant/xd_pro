package com.xiaodou.rabbitmqagent.client;

import java.io.IOException;

import com.rabbitmq.client.QueueingConsumer;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 从rabbit-mq中读取数据并处理， 其中dealMessage是一个空模板方法未实现 需要调用者自己实现
 * 
 * Date: 2014/4/23 Time: 18:30
 * 
 * @author Tian.Dong
 */
public class RabbitReader extends EndPoint {

  @Override
  public void init() throws IOException {
    super.init();
    channel.queueDeclare(this.getEndPointName(), false, false, false, null);
    /* 绑定路由规则-队列名 */
    channel.queueBind(this.getEndPointName(), this.getExchangeName(), this.getEndPointName());
  }


  public void readMessageAndDo() throws InterruptedException {
    QueueingConsumer consumer = new QueueingConsumer(channel);
    /* 设置为不自动应答 */
    boolean autoAck = false;
    try {
      channel.basicConsume(this.getEndPointName(), autoAck, consumer);
    } catch (IOException e) {
      LoggerUtil.error("channel.basicConsume IO异常", e);
    }
    while (true) {
      /* 从队列中循环读取消息 */
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());

      /* 处理消息 */
      dealMessage(message);

      /* 处理完之后应答，否则mq服务器会死等 */
      try {
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      } catch (IOException e) {
        LoggerUtil.error("channel.basicAck IO异常", e);
      }
    }

  }

  public void dealMessage(String message) {}
}
