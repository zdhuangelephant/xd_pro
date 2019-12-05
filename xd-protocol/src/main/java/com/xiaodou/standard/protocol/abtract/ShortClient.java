package com.xiaodou.standard.protocol.abtract;

import java.io.IOException;
import java.util.UUID;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.standard.protocol.CommunicatAble;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.TellAble;
import com.xiaodou.standard.protocol.UniqueAble;
import com.xiaodou.standard.protocol.exception.CommunicateException;
import com.xiaodou.standard.protocol.exception.TellException;
import com.xiaodou.standard.protocol.exception.CommunicateException.ConnectException;

/**
 * @name @see com.xiaodou.standard.protocol.abtract.SimpleClient.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 基础Client
 * @version 1.0
 */
public abstract class ShortClient implements TellAble, CommunicatAble, UniqueAble {

  public ShortClient(TargetSocket targetServer) {
    this.uniqueId = UUID.randomUUID().toString();
  }

  /** uniqueId 唯一标记ID */
  private String uniqueId;

  private TargetSocket server;

  public String getUniqueId() {
    return uniqueId;
  }

  public TargetSocket getServer() {
    return server;
  }

  @Override
  public String uniqueId() {
    return uniqueId;
  }

  /**
   * 默认异常处理器
   * 
   * @param e 异常
   * @throws TellException 异常
   */
  protected <T extends Exception> void processException(T e) throws T {
    LoggerUtil.error(e.getMessage(), e);
    throw e;
  }

  @Override
  public <T extends MessageAble> boolean tell(T message) throws TellException {
    try {
      if (isAlive()) {
        return tell0(message);
      } else {
        return false;
      }
    } catch (Exception e) {
      processException(new TellException(message, (IOException) e));
    }
    return false;
  }

  /**
   * 倾诉方法实现
   * 
   * @param message 消息
   * @return 结果
   * @throws Exception 异常
   */
  public abstract <T extends MessageAble> boolean tell0(T message) throws Exception;

  @Override
  public void connect() throws ConnectException {
    try {
      connect0();
    } catch (Exception e) {
      processException(new ConnectException(server, (IOException) e));
    }
  }

  /**
   * 连接实现方法
   * 
   * @throws Exception 异常信息
   */
  public abstract void connect0() throws Exception;

  @Override
  public void reConnect() throws ConnectException {
    try {
      reConnect0();
    } catch (Exception e) {
      processException(new ConnectException(server, (IOException) e));
    }
  }

  /**
   * 重连实现方法
   * 
   * @throws Exception 异常信息
   */
  public abstract void reConnect0() throws Exception;

  @Override
  public boolean isAlive() throws CommunicateException {
    try {
      return isAlive0();
    } catch (Exception e) {
      processException(new CommunicateException(server, (IOException) e));
    }
    return false;
  }

  /**
   * 判活实现方法
   * 
   * @return 当前状态 true/false
   * @throws Exception 异常信息
   */
  public abstract boolean isAlive0() throws Exception;

  /**
   * 获取Client使用端口
   * 
   * @return 使用端口号
   */
  public abstract Integer usePort();
}
