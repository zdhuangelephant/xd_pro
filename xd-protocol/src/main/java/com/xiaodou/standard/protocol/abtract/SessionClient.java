package com.xiaodou.standard.protocol.abtract;

import java.io.IOException;

import com.xiaodou.standard.protocol.SessionAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.exception.CommunicateException;
import com.xiaodou.standard.protocol.manager.SessionClientManager;

/**
 * @name @see com.xiaodou.standard.protocol.abtract.SessionClient.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 长连接客户端
 * @version 1.0
 */
public abstract class SessionClient extends SimpleServer implements SessionAble {

  /**
   * 构造方法
   * 
   * @param server 目标server信息
   */
  public SessionClient(TargetSocket server) {
    super(server);
    SessionClientManager.regist(this);
  }

  @Override
  public boolean heartBeat() throws CommunicateException {
    try {
      return heartBeat0();
    } catch (Exception e) {
      processException(new CommunicateException(getServer(), (IOException) e));
    }
    return false;
  }

  /**
   * 心跳实现方法
   * 
   * @return 心跳结果
   * @throws Exception 异常信息
   */
  public abstract boolean heartBeat0() throws Exception;
}
