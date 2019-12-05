package com.xiaodou.amqp.connectpool;

import com.rabbitmq.client.ConnectionFactory;
import com.xiaodou.amqp.exception.AmqpClientException;

/**
 * 创建连接的工厂类
 *
 * @author heshixiong
 */
public class RabbitProxyFactory {
  private ConnectionFactory _factory;
  private int _sendTimeOut;
  private int _receiveTimeOut;
  private int _connectionTimeOut;
  private int _qos = 0;

  /**
   * 构造函数
   *
   * @param factory           Rabbitmq连接创建类
   * @param sendTimeOut       发送超时时间
   * @param receiveTimeOut    接收超时时间
   * @param connectionTimeOut 连接超时时间
   * @param qos               消息接收数目
   */
  public RabbitProxyFactory(ConnectionFactory factory, int sendTimeOut, int receiveTimeOut,
      int connectionTimeOut, int qos) {
    this._factory = factory;
    this._sendTimeOut = sendTimeOut;
    this._receiveTimeOut = receiveTimeOut;
    this._connectionTimeOut = connectionTimeOut;
    this._qos = qos;
  }

  /**
   * 创建发送端连接
   *
   * @return 消息发送代理
   * @throws AmqpClientException
   */
  public RabbitSendProxy getSendProxy() throws AmqpClientException {
    return new RabbitSendProxy(_factory, _sendTimeOut, _receiveTimeOut, _connectionTimeOut, _qos);
  }

  /**
   * 创建接收端连接
   *
   * @return 消息接收代理
   * @throws AmqpClientException
   */
  public RabbitReceiveProxy getReceiveProxy() throws AmqpClientException {
    return new RabbitReceiveProxy(_factory, _sendTimeOut, _receiveTimeOut, _connectionTimeOut, _qos);
  }
}
