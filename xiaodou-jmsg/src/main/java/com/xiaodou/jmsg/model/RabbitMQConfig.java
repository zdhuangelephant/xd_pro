package com.xiaodou.jmsg.model;

import java.lang.reflect.Field;
/**
 * @name @see com.xiaodou.jmsg.model.RabbitMQConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 消息配置信息
 * @version 1.0
 */
public class RabbitMQConfig {

  private String serverIp;
  private int port;
  private String userName;
  private String passWord;
  private int maxPoolSize;
  private int requestedHeartbeat;
  private int requestedConnectionTimeOut;
  private String failedLogBaseDir;
  private int connectionTimeOut;
  private int sendTimeOut;
  private int receiveTimeOut;
  private String sendLogBaseDir;

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public int getRequestedHeartbeat() {
    return requestedHeartbeat;
  }

  public void setRequestedHeartbeat(int requestedHeartbeat) {
    this.requestedHeartbeat = requestedHeartbeat;
  }

  public int getRequestedConnectionTimeOut() {
    return requestedConnectionTimeOut;
  }

  public void setRequestedConnectionTimeOut(int requestedConnectionTimeOut) {
    this.requestedConnectionTimeOut = requestedConnectionTimeOut;
  }

  public String getFailedLogBaseDir() {
    return failedLogBaseDir;
  }

  public void setFailedLogBaseDir(String failedLogBaseDir) {
    this.failedLogBaseDir = failedLogBaseDir;
  }

  public int getConnectionTimeOut() {
    return connectionTimeOut;
  }

  public void setConnectionTimeOut(int connectionTimeOut) {
    this.connectionTimeOut = connectionTimeOut;
  }

  public int getSendTimeOut() {
    return sendTimeOut;
  }

  public void setSendTimeOut(int sendTimeOut) {
    this.sendTimeOut = sendTimeOut;
  }

  public int getReceiveTimeOut() {
    return receiveTimeOut;
  }

  public void setReceiveTimeOut(int receiveTimeOut) {
    this.receiveTimeOut = receiveTimeOut;
  }

  public String getSendLogBaseDir() {
    return sendLogBaseDir;
  }

  public void setSendLogBaseDir(String sendLogBaseDir) {
    this.sendLogBaseDir = sendLogBaseDir;
  }

  public static void main(String[] args) {
    for (Field field : RabbitMQConfig.class.getDeclaredFields()) {
      String fieldName = field.getName();
      StringBuilder newName = new StringBuilder(20);
      for (int i = 0; i < fieldName.length(); i++) {
        char c = fieldName.charAt(i);
        if (c == (Character.toLowerCase(c)))
          newName.append(c);
        else
          newName.append("_").append(Character.toLowerCase(c));
      }
      System.out.println(String.format("<result column=\"%s\" property=\"%s\"></result>", newName,
          fieldName));
    }
  }

}
