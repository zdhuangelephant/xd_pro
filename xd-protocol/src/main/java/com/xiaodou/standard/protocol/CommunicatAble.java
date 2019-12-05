package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.CommunicateException;
import com.xiaodou.standard.protocol.exception.CommunicateException.ConnectException;

/**
 * @name @see com.xiaodou.standard.protocol.CommunicatAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 可通信性
 * @version 1.0
 */
public interface CommunicatAble {

  /**
   * 建立连接
   * 
   * @throws ConnectException 通信连接异常
   */
  public void connect() throws ConnectException;

  /**
   * 判断活跃状态
   * 
   * @return 当前活跃状态:true/false
   * @throws CommunicateException 通信异常
   */
  public boolean isAlive() throws CommunicateException;

  /**
   * 重新建立连接
   * 
   * @throws ConnectException 通信连接异常
   */
  public void reConnect() throws ConnectException;
}
