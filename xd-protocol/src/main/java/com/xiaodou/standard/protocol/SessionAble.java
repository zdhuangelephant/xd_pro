package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.CommunicateException;

/**
 * @name @see com.xiaodou.standard.protocol.SessionAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 会话性
 * @version 1.0
 */
public interface SessionAble {

  /**
   * 心跳
   * 
   * @return 结果
   * @throws CommunicateException 通信异常
   */
  public boolean heartBeat() throws CommunicateException;
}
