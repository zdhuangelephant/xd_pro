package com.xiaodou.protocolnetty.server;

import org.apache.log4j.Logger;

import com.xiaodou.protocolnetty.proxy.SimpleServerProxy;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.SimpleServer;

/**
 * @name BasicServer CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月13日
 * @description basicServer
 * @version 1.0
 */
public abstract class BasicServer extends SimpleServer {

  Logger logger = Logger.getLogger(BasicServer.class);

  private TargetSocket server;

  private SimpleServerProxy serverProxy;

  // 构造方法
  public BasicServer(TargetSocket server) {
    super(server);
    serverProxy = new SimpleServerProxy(server, this);
    this.server = server;
  }

  @Override
  public boolean shutDown0() throws Exception {
    boolean shutdown = false;

    try {
      shutdown = serverProxy.shutDown0();
    } catch (Exception e) {
      logger.debug(e.getMessage());
      e.printStackTrace();
    }
    return shutdown;
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
