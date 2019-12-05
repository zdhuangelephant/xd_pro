package com.xiaodou.amqp.connectpool;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 发送端代理
 * 
 */
public class RabbitSendProxy extends RabbitProxy implements ConfirmListener {

  private ConfirmCallback confirmCallback;
  private final SortedMap<Long, String> unconfirmedMessages = Collections
      .synchronizedSortedMap(new TreeMap<Long, String>());

  public RabbitSendProxy(ConnectionFactory factory, int sendTimeOut, int receiveTimeOut,
      int connectionTimeOut, int qos) throws AmqpClientException {
    super(factory, sendTimeOut, receiveTimeOut, connectionTimeOut, qos);
    try {
      channel.addConfirmListener(this);
      channel.confirmSelect();
    } catch (Exception e) {
      this.disPose();
      throw new AmqpClientException("Channel confirmSelect failed: ", e);
    }
  }

  /**
   * 对消息实体进行序列化并发送
   * 
   * @param exchangeName 需要注册的exchange
   * @param msg 消息实体
   * @param routeKey 路由规则
   * @return 发送完成，返回true
   */
  public boolean send(String exchangeName, String msg, String routeKey) throws AmqpClientException {
    byte[] msgToSend = CodecHelper.parseStringToByte(msg);
    checkMsgLength(msgToSend);

    long key = channel.getNextPublishSeqNo();
    try {
      unconfirmedMessages.put(key, msg); // 必须发送前缓存，若发送后缓存有可能异步回调早于缓存
      channel.basicPublish(exchangeName, routeKey, false, MessageProperties.PERSISTENT_TEXT_PLAIN,
          msgToSend);
    } catch (Exception e) {
      unconfirmedMessages.remove(key);
      setNotAvailable();
      throw new AmqpClientException("send msg error", e);
    }
    return true;
  }

  public void setConfirmCallback(ConfirmCallback callback) {
    this.confirmCallback = callback;
  }


  /**
   * 销毁连接
   */
  @Override
  public void disPose() {
    super.disPose();
    channel.clearConfirmListeners();
    if (unconfirmedMessages != null && unconfirmedMessages.size() > 0) {
      synchronized (unconfirmedMessages) {
        Iterator<Entry<Long, String>> it = unconfirmedMessages.entrySet().iterator();
        while (it.hasNext()) {
          Entry<Long, String> entry = it.next();
          it.remove();
          String message = entry.getValue();
          if (confirmCallback != null && message != null) {
            confirmCallback.confirm(message, false, ConfirmCallback.NOT_RECEIVED);
          }
        }
      }
    }
  }

  @Override
  public void handleAck(long deliveryTag, boolean multiple) throws IOException {
    doHandleCallback(deliveryTag, multiple, true);
  }

  @Override
  public void handleNack(long deliveryTag, boolean multiple) throws IOException {
    doHandleCallback(deliveryTag, multiple, false);
  }

  private void doHandleCallback(long deliveryTag, boolean multiple, boolean ack) {
    if (!multiple) {
      String message = unconfirmedMessages.remove(deliveryTag);
      if (confirmCallback != null && message != null) {
        confirmCallback.confirm(message, ack, ConfirmCallback.SERVER_RESPONSE);
      }
    } else {
      synchronized (unconfirmedMessages) {
        SortedMap<Long, String> involvedConfirms = unconfirmedMessages.headMap(deliveryTag + 1);
        Iterator<Entry<Long, String>> it = involvedConfirms.entrySet().iterator();
        while (it.hasNext()) {
          Entry<Long, String> entry = it.next();
          it.remove();
          String message = entry.getValue();
          if (confirmCallback != null && message != null) {
            confirmCallback.confirm(message, ack, ConfirmCallback.SERVER_RESPONSE);
          }
        }
      }
    }
  }

  private void checkMsgLength(byte[] msgToSend) {
    final int maxLength = 64 * 1024;
    if (msgToSend.length > maxLength) {
      String error = "message length(" + msgToSend.length + "B) is longer than " + maxLength + "B";
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException(error);
      LoggerUtil.error(error, illegalArgumentException);
      throw illegalArgumentException;
    }
  }

}
