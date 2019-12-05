package com.xiaodou.amqp.connectpool.receiverdispatch;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 消息分发器管理类,单例类
 * 
 * @author heshixiong
 */
public class RabbitReceiverDispatcher {
  private static RabbitReceiverDispatcher singleTon = null;

  private static final Object singleInstanceLock = new Object();

  private RabbitReceiverDispatcher() {}

  public static RabbitReceiverDispatcher getInstance() {
    if (singleTon == null) {
      synchronized (singleInstanceLock) {
        if (singleTon == null) {
          singleTon = new RabbitReceiverDispatcher();
        }
      }
    }
    return singleTon;
  }

  private Map<String, MessageReceiver> _msgReceiverMap = Maps.newConcurrentMap();

  /**
   * 注册监听器,接收到消息之后通知接口开始处理任务
   * 
   * @param queueName 需要监听的队列的名称
   * @param listenerClass 接收的消息的类型
   * @param parallelCount 多线程接收消息,指定线程数目
   * @param autoAck 是否采用autoAck
   * @param qos 指定接收消息的数目
   */
  public void registerListener(Class<? extends MessageListener> listenerClass, String queueName,
      int parallelCount, boolean autoAck, int qos) {
    MessageReceiver receiver;
    if (!_msgReceiverMap.containsKey(queueName)) {
      receiver = new MessageReceiver(listenerClass, queueName, parallelCount, qos, autoAck);
      _msgReceiverMap.put(queueName, receiver);
    } else {
      receiver = _msgReceiverMap.get(queueName);
    }
    receiver.start();
  }

  /**
   * 销毁创建的所有接收器
   */
  @Override
  protected void finalize() throws Throwable {
    if (_msgReceiverMap != null) {
      for (MessageReceiver msgReceiver : _msgReceiverMap.values()) {
        if (msgReceiver != null) {
          msgReceiver.stop();
        }
      }
      _msgReceiverMap.clear();
      _msgReceiverMap = null;
    }
    super.finalize();
  }
}
