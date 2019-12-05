package com.xiaodou.protocolnetty;

import org.junit.Test;

import com.xiaodou.protocolnetty.server.BasicServer;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.exception.CommunicateException;
import com.xiaodou.standard.protocol.exception.CommunicateException.ConnectException;
import com.xiaodou.standard.protocol.exception.ShutDownException;


public class TestBasicServer {

  TargetSocket socket = new TargetSocket("localhost", 2018);
  BasicServer basicServer = new BasicServer(socket) {
    @Override
    public <T extends MessageAble> boolean listen0(T message) throws Exception {
      return false;
    }

  };

  @Test
  public void testConnect() {
    try {
      basicServer.connect();
    } catch (ConnectException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testShutDown() {
    try {
      basicServer.shutDown();
    } catch (ShutDownException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Test
  public void shutDown() {
    try {
      boolean shutdown = basicServer.shutDown0();
      System.out.println("shut" + shutdown);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // @Test
  // public <T extends MessageAble> void listen0(T message) {
  // TDO Auto-generated method stub
  // false;
  // }

  // @Test
  // public void tell0() {
  // basicServer.tell0(message);
  // }

  @Test
  public void reConnect() {
    try {
      basicServer.reConnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testIsAlive() {
    try {
      basicServer.isAlive();
    } catch (CommunicateException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void usePort() {
    basicServer.getServer().getPort();
  }

  @Test
  public void testconnect() {
    try {
      basicServer.connect();
    } catch (ConnectException e) {
      e.printStackTrace();
    }
  }
}
