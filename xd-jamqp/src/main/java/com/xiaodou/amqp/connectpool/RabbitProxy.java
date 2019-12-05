package com.xiaodou.amqp.connectpool;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.util.timehelper.AMQPTimer;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 客户端代理接口,做为连接池中的单元连接代理,可以由发送端和接收端继承
 */
public abstract class RabbitProxy {

  private volatile boolean isAvailable = true;
  private long createdTime = AMQPTimer.getCurrentTime();
  private DisposeListener disposeListener;
  private int connectionTimeOut = 0;
  private int receiveTimeOut = 0;
  //private int sendTimeOut = 0;

  private Connection connection;

  protected int qos = 0;
  protected Channel channel;

  /**
   * 构造方法,创建一个连接
   *
   * @param factory
   * @param sendTimeOut
   * @param receiveTimeOut
   * @param connectionTimeOut
   * @param qos
   * @throws AmqpClientException
   */
  public RabbitProxy(ConnectionFactory factory, int sendTimeOut, int receiveTimeOut,
      int connectionTimeOut, int qos) throws AmqpClientException {
    try {
      this.connection = factory.newConnection();
      this.channel = connection.createChannel();
      this.channel.basicQos(0, qos, false);
    } catch (Exception e) {
      throw new AmqpClientException("Connection create failed: ", e);
    }
    //    this.sendTimeOut = sendTimeOut;
    this.receiveTimeOut = receiveTimeOut;
    this.connectionTimeOut = connectionTimeOut;
  }

  /**
   * 接收端构造方法
   *
   * @return
   */
  public RabbitProxy() {
  }

  public long getCreatedTime() {
    return createdTime;
  }

  public int getConnectionTimeOut() {
    return connectionTimeOut;
  }

  protected int getReceiveTimeOut() {
    return receiveTimeOut;
  }

  /**
   * 判断连接是否可用
   *
   * @return
   */
  public boolean isAvailable() {
    if (!isAvailable) {
      return false;
    }

    if (connection == null || !connection.isOpen()) {
      return false;
    }

    if (channel == null || !channel.isOpen()) {
      return false;
    }

    long currentTime = AMQPTimer.getCurrentTime();
    if (currentTime - createdTime >= connectionTimeOut) {
      return false;
    }

    return true;
  }

  /**
   * 设置该连接不可用
   */
  public void setNotAvailable() {
    isAvailable = false;
  }

  public void setDisposeListener(DisposeListener listener) {
    this.disposeListener = listener;
  }

  //  /**
  //   * 确保连接可用
  //   */
  //  public boolean ensureAvaliable(Exception e) {
  //    if (e instanceof SocketException) {
  //      isAvailable = false;
  //    } else if (e instanceof IOException) {
  //      isAvailable = false;
  //    } else if (e instanceof Exception) {
  //      isAvailable = false;
  //    }
  //    return isAvailable;
  //  }

  protected void disPose() {
    isAvailable = false;
    if (channel != null && channel.isOpen()) {
      try {
        channel.close();
      } catch (Exception e) {
        LoggerUtil.error("close channel error: ", e);
      }
    }

    if (connection != null && connection.isOpen()) {
      try {
        connection.close();
      } catch (Exception e) {
        LoggerUtil.error("close connection error: ", e);
      }
    }

    if (disposeListener != null) {
      try {
        disposeListener.proxyDisposed(this);
      } catch (Exception e) {
        LoggerUtil.error("invoke dispose listener error: ", e);
      }
    }
  }

}
