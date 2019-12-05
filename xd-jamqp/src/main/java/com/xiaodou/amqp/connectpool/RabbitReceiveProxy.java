package com.xiaodou.amqp.connectpool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.messagentity.InternalMessageEntity;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;

/**
 * RabbitMQ接收端代理类
 */

public class RabbitReceiveProxy extends RabbitProxy {

  private Map<String, QueueingConsumer> consumerMap = Collections
      .synchronizedMap(new HashMap<String, QueueingConsumer>());
  private final AtomicInteger useCount = new AtomicInteger(0);
  private final Object lock = new Object();
  private boolean isDisposed = false;

  /**
   * 构造函数,确定要连接的RabbitMQ服务器和要注册的queueName
   * 
   * @param factory 连接工厂
   * @param sendTimeOut 发送超时时间
   * @param receiveTimeOut 接收超时时间
   * @param connectionTimeOut 连接建立超时时间
   * @throws AmqpClientException
   */
  public RabbitReceiveProxy(ConnectionFactory factory, int sendTimeOut, int receiveTimeOut,
      int connectionTimeOut, int qos) throws AmqpClientException {
    super(factory, sendTimeOut, receiveTimeOut, connectionTimeOut, qos);
    this.qos = qos;
  }

  /**
   * 增加使用计数
   * 
   * @return 更新后的值;proxy已失效，无效操作则返回-1.
   */
  public int increaseUseCount() {
    int result = -1;
    if (!isDisposed) {
      synchronized (lock) {
        if (!isDisposed) {
          result = this.useCount.incrementAndGet();
        }
      }
    }
    return result;
  }

  /**
   * 减少使用计数
   * 
   * @return 更新后的值;proxy已失效，无效操作则返回-1.
   */
  public int decreaseUseCount() {
    int result = -1;
    if (!isDisposed) {
      synchronized (lock) {
        if (!isDisposed) {
          result = this.useCount.decrementAndGet();
        }
      }
    }
    return result;
  }

  /**
   * 开始监听队列
   * 
   * @param queueName 队列名称
   * @param autoAck 是否采用autoAck模式
   * @throws IOException
   */
  public void beginConsume(String queueName, boolean autoAck) throws IOException {
    if (!consumerMap.containsKey(queueName)) {
      QueueingConsumer consumer = new QueueingConsumer(channel);
      channel.basicConsume(queueName, autoAck, consumer);
      consumerMap.put(queueName, consumer);
    }
  }

  /**
   * 获取下一条数据信息,接收String类型的消息
   * 
   * @param queueName 队列名称
   * @param timeout 获取下一条消息或直到timeout时间后返回null
   * @return 返回接收到的消息
   */
  public InternalMessageEntity next(String queueName, long timeout) throws AmqpClientException {
    QueueingConsumer consumer = consumerMap.get(queueName);
    if (consumer == null) {
      throw new AmqpClientException(
          "Consumer of %s is null. You should call beginConsume before this invoke.");
    }
    if (isAvailable()) {
      try {
        QueueingConsumer.Delivery delivery = consumer.nextDelivery(timeout);
        if (delivery == null) {
          return null;
        }
        byte[] body = delivery.getBody();
        if (body == null) {
          return null;
        }
        ByteBuffer buffer = ByteBuffer.wrap(body);
        String message = CodecHelper.parseByteBufferToString(buffer);
        long tag = delivery.getEnvelope().getDeliveryTag();
        return new InternalMessageEntity(message, tag);
      } catch (Exception e) {
        setNotAvailable();
        throw new AmqpClientException("Get next delivery error", e);
      }
    }
    return null;
  }

  /**
   * 销毁连接
   */
  @Override
  protected void disPose() {
    super.disPose();
    if (consumerMap != null) {
      consumerMap.clear();
    }
  }

  /**
   * 尝试关闭连接.连接不可用且使用计数为0时，则关闭;否则不关闭.
   * 
   * @return 连接关闭，返回true;否则，返回false.
   */
  public boolean tryDispose() {
    if (!isAvailable() && !isDisposed && this.useCount.get() == 0) {
      synchronized (lock) {
        if (!isAvailable() && !isDisposed && this.useCount.get() == 0) {
          this.disPose();
          isDisposed = true;
        }
      }
    }

    return isDisposed;
  }

  /**
   * ack确认
   */
  public void ack(long tag) {
    try {
      this.channel.basicAck(tag, false);
    } catch (Exception e) {
      setNotAvailable();
    }
  }

  /**
   * ack拒绝
   */
  public void reject(long tag, boolean requeue) {
    try {
      this.channel.basicReject(tag, requeue);
    } catch (Exception e) {
      setNotAvailable();
    }
  }
}
