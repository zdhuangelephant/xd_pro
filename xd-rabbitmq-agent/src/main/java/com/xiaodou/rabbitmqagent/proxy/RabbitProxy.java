package com.xiaodou.rabbitmqagent.proxy;

import java.io.IOException;

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.rabbitmqagent.pool.RabbitPool;

/**
 * Date: 2014/5/5 Time: 15:27
 *
 * @author Tian.Dong
 */
public class RabbitProxy {
  /**
   * 判定此proxy超时不可用毫秒数
   */
  private final long timeout = 60000L;

  /**
   * exchangeName
   */
  private String exchangeName;
  /**
   * 如果路由规则为direct 直连一对一
   * 则队列名和路由规则一致
   */
  private String endPointName;

  /**
   * rabbit-mq服务器 Host
   */
  private String hostName;
  /**
   * rabbit-mq服务器 端口
   */
  private int hostPort;

  /**
   * rabbit-mq 服务器用户名
   */
  private String username;
  /**
   * rabbit-mq 服务器密码
   */
  private String password;
  protected Channel channel;
  protected Connection connection;
  protected QueueingConsumer consumer;

  public QueueingConsumer getConsumer() {
    return consumer;
  }

  public void setConsumer(QueueingConsumer consumer) {
    this.consumer = consumer;
  }

  /**
   * 此proxy对象所属连接池
   */
  RabbitPool rabbitPool;
  /**
   * 可用性
   */
  private boolean isAvailable;
  /**
   * 此proxy对象创建时间
   */
  private long createTime;

  /**
   * 是否初始化
   */
  private boolean isInit = false;


  public RabbitProxy() {
    isAvailable = true;
    createTime = System.currentTimeMillis();
  }

  /**
   * 初始化
   */
  public void init() throws IOException {
    init(1);
  }

  /**
   * 初始化
   */
  public void init(int qosCount) throws IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostName);
    factory.setPort(hostPort);
    factory.setUsername(username);
    factory.setPassword(password);
        /*超时时间500毫秒*/
    factory.setConnectionTimeout(500);
        /*10秒*/
    factory.setRequestedHeartbeat(10);
    this.connection = factory.newConnection();
    this.channel = connection.createChannel();

//        channel.exchangeDeclare("giftcard","direct");
//        channel.queueDeclare("giftcard", false, false, false, null);
//        channel.queueBind("giftcard","giftcard","giftcard");

    try {
      channel.basicQos(qosCount);
    } catch (IOException e) {
      LoggerUtil.error("设置basicQos异常", e);
    }
    isInit = true;
  }

  public void initConsume() throws IOException {
    init(1);
    consumer = new QueueingConsumer(channel);
    channel.basicConsume(getEndPointName(), false, consumer);
  }

  /**
   * 放回池中
   */
  public void release() {
    this.rabbitPool.returnProxy(this);
  }

  /**
   * 发送消息
   *
   * @param message 消息
   */
  public void sendMessage(String message) throws IOException {
    channel.basicPublish(this.getExchangeName(), this.getEndPointName(), null, message.getBytes());
  }


  /**
   * 关闭连接
   */
  public void close() {
    try {
      if (channel != null) {
        channel.close();
      }
      if (connection != null) {
        connection.close();
      }

    } catch (AlreadyClosedException e) {
    } catch (IOException e) {
      LoggerUtil.error("RabbitProxy close IOException", e);
    } catch (Exception e) {
      LoggerUtil.error("RabbitProxy close Exception", e);
    }
  }

  public boolean isAvailable() {
    if (System.currentTimeMillis() - createTime > timeout) {
      return false;
    }
    return isAvailable;
  }


  public String getExchangeName() {
    return exchangeName;
  }

  public void setExchangeName(String exchangeName) {
    this.exchangeName = exchangeName;
  }

  public String getEndPointName() {
    return endPointName;
  }

  public void setEndPointName(String endPointName) {
    this.endPointName = endPointName;
  }

  public RabbitPool getRabbitPool() {
    return rabbitPool;
  }

  public void setRabbitPool(RabbitPool rabbitPool) {
    this.rabbitPool = rabbitPool;
  }

  public long getTimeout() {
    return timeout;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public int getHostPort() {
    return hostPort;
  }

  public void setHostPort(int hostPort) {
    this.hostPort = hostPort;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public Channel getChannel() {
    return channel;
  }

  public boolean isInit() {

    return isInit;
  }

  public void setInit(boolean isInit) {
    this.isInit = isInit;
  }

  public Connection getConnection() {
    return connection;
  }

}