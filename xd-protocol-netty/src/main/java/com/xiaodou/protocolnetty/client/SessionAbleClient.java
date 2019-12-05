package com.xiaodou.protocolnetty.client;

import com.xiaodou.protocolnetty.proxy.SessionClientProxy;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.SessionClient;

/**
 * @name SessionAbleServer CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月13日
 * @description 长链接
 * @version 1.0
 */
public abstract class SessionAbleClient extends SessionClient {

  private TargetSocket server;
  private SessionClientProxy serverProxy;

  public SessionAbleClient(TargetSocket server) {
    super(server);
    this.server = server;
    this.serverProxy = new SessionClientProxy(server);
  }

  @Override
  // 可以通过其他方法实现
  public boolean heartBeat0() throws Exception {
    return serverProxy.isAlive0();
  }

  @Override
  public boolean shutDown0() throws Exception {
    return serverProxy.shutDown0();
  }

  @Override
  public <T extends MessageAble> boolean tell0(T message) throws Exception {
    return serverProxy.tell0(message);
  }


  @Override
  public void reConnect0() throws Exception {
    serverProxy.reConnect0();
  }

  @Override
  public boolean isAlive0() throws Exception {
    return serverProxy.isAlive0();
  }

  @Override
  public Integer usePort() {
    return server.getPort();
  }

  @Override
  public void connect0() throws Exception {
    serverProxy.connect0();
  }
}
