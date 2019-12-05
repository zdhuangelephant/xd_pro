package com.xiaodou.amqp.connectpool.receiverdispatch;

import com.xiaodou.amqp.connectpool.RabbitReceiveProxy;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.messagentity.InternalMessageEntity;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 消息处理类
 */
public class MessageProcessTask implements Runnable {

  private final Class<? extends MessageListener> listener;
  private final InternalMessageEntity messageEntity;
  private final String queueName;
  private final boolean autoAck;
  private final RabbitReceiveProxy proxy;

  public MessageProcessTask(Class<? extends MessageListener> listenerClass, String queueName,
      boolean autoAck, InternalMessageEntity messageEntity, RabbitReceiveProxy proxy)
      throws AmqpClientException {
    this.listener = listenerClass;
    this.messageEntity = messageEntity;
    this.autoAck = autoAck;
    this.queueName = queueName;
    this.proxy = proxy;
    if (this.proxy.increaseUseCount() <= 0) { // 任务创建时，增加proxy使用计数，且必须增加成功
      throw new AmqpClientException("Unexpected proxy use count increase result.");
    }
  }

  @Override
  public void run() {
    try {
      MessageListener newInstance = listener.newInstance();
      newInstance.receive(messageEntity.getMessage(), queueName);
      if (!autoAck) {
        this.proxy.ack(messageEntity.getTag());
      }
    } catch (Exception e) {
      LoggerUtil.error("doTask error: ", e);
      if (!autoAck) {
        this.proxy.reject(messageEntity.getTag(), true);
      }
    } finally {
      proxy.decreaseUseCount(); // 处理完成减少proxy使用计数
      proxy.tryDispose();
    }
  }
}
