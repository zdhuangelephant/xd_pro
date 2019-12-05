package com.xiaodou.protocolnetty.client;

import org.apache.log4j.Logger;

import com.xiaodou.protocolnetty.proxy.ClientProxy;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.ShortClient;

/**
 * @name TempClient CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月23日
 * @description 短连接
 * @version 1.0
 */
public class TempClient extends ShortClient {

  Logger logger = Logger.getLogger(TempClient.class);

  private ClientProxy clientProxy;
  private TargetSocket targetSocket;

  public TempClient(TargetSocket targetServer) {
    super(targetServer);
    this.targetSocket = targetServer;
    clientProxy = new ClientProxy(targetServer);
  }

  @Override
  public <T extends MessageAble> boolean tell0(T message) throws Exception {
    return clientProxy.tell0(message);
  }

  @Override
  public void connect0() throws Exception {
    try {
      clientProxy.connect0();
    } catch (Exception e) {
      reConnect0();// 连接失败以后尝试重新连接
      e.printStackTrace();
    }
  }

  @Override
  public void reConnect0() throws Exception {
    clientProxy.reConnect0();
  }

  @Override
  public boolean isAlive0() throws Exception {
    return clientProxy.isAlive0();
  }

  @Override
  public Integer usePort() {
    return targetSocket.getPort();
  }

}
