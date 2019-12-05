package com.xiaodou.rabbitmqagent.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;


/**
 * Date: 2014/4/23 Time: 14:55
 * 
 * @author Tian.Dong
 */

public class EndPoint {
  /**
   * exchangeName
   */
  private String exchangeName;
  /**
   * 如果路由规则为direct 直连一对一 则队列名和路由规则一致
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
  protected boolean isInit = false;

  public void init() throws IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostName);
    factory.setPort(hostPort);
    factory.setUsername(username);
    factory.setPassword(password);
    /* 超时时间500毫秒 */
    factory.setConnectionTimeout(500);
    /* 10秒 */
    factory.setRequestedHeartbeat(10);
    this.connection = factory.newConnection();
    this.channel = connection.createChannel();

    isInit = true;
  }

  public void setInit(boolean isInit) {
    this.isInit = isInit;
  }

  public boolean isInit() {
    return isInit;

  }

  public void close() throws IOException {
    channel.close();
    connection.close();
  }

  public String getEndPointName() {
    return endPointName;
  }

  public void setEndPointName(String endPointName) {
    this.endPointName = endPointName;
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

  public String getExchangeName() {
    return exchangeName;
  }

  public void setExchangeName(String exchangeName) {
    this.exchangeName = exchangeName;
  }
}
