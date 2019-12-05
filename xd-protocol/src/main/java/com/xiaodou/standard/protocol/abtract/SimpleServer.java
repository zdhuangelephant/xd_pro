package com.xiaodou.standard.protocol.abtract;

import java.io.IOException;

import com.xiaodou.standard.protocol.ListenAble;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.ShutDownAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.exception.ListenException;
import com.xiaodou.standard.protocol.exception.ShutDownException;

/**
 * @name @see com.xiaodou.standard.protocol.abtract.SimpleServer.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 基础服务端
 * @version 1.0
 */
public abstract class SimpleServer extends ShortClient implements ListenAble, ShutDownAble {

  public SimpleServer(TargetSocket server) {
    super(server);
  }

  @Override
  public void shutDown() throws ShutDownException {
    try {
      shutDown0();
    } catch (Exception e) {
      processException(new ShutDownException(uniqueId(), usePort(), (IOException) e));
    }
  }

  /**
   * 关闭实现方法
   * 
   * @return 关闭结果
   * @throws Exception 异常信息
   */
  public abstract boolean shutDown0() throws Exception;

  @Override
  public <T extends MessageAble> boolean listen(T message) throws ListenException {
    try {
      return listen0(message);
    } catch (Exception e) {
      processException(new ListenException(message, (IOException) e));
    }
    return false;
  }

  /**
   * 倾听消息实现方法
   * 
   * @param message 消息
   * @return 倾听结果
   * @throws Exception 异常信息
   */
  public abstract <T extends MessageAble> boolean listen0(T message) throws Exception;
}
