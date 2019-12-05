package com.xiaodou.protocolnetty;

import org.junit.Test;

import com.xiaodou.protocolnetty.client.SessionAbleClient;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.exception.CommunicateException;
import com.xiaodou.standard.protocol.exception.CommunicateException.ConnectException;
import com.xiaodou.standard.protocol.exception.ShutDownException;
import com.xiaodou.standard.protocol.exception.TellException;

public class TestSessionClient {

  TargetSocket socket = new TargetSocket("localhost", 2018);

  SessionAbleClient client = new SessionAbleClient(socket) {
    @Override
    public <T extends MessageAble> boolean listen0(T message) throws Exception {
      return false;
    }
  };

  MessageAble message = new MessageAble() {

    @Override
    public String uniqueMessageName() {
      return "uniqueMessage";
    }

    @Override
    public TargetSocket to() {
      return new TargetSocket("localhost", 2018);
    }

    @Override
    public String messageContent() {
      return "content";
    }

    @Override
    public TargetSocket from() {
      return new TargetSocket("localhost", 2018);
    }

    @Override
    public void setUniqueMessageName(String uniqueMessageName) {
      // TODO Auto-generated method stub

    }

    @Override
    public void setMessageContent(String messageContent) {
      // TODO Auto-generated method stub

    }
  };

  @Test
  public void testConnect() {
    try {
      client.connect();
    } catch (ConnectException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testShutDown() {
    try {
      client.shutDown();
    } catch (ShutDownException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  // 可以通过其他方法实现
  @Test
  public void testHeartBeat() {
    // 调用代理的 isalive方法
    try {
      client.heartBeat();
    } catch (CommunicateException e) {
      e.printStackTrace();
    }
  }

  //
  // public <T extends MessageAble> void listen0(T message) throws Exception {
  // // TODO Auto-generated method stub
  // false;
  // }

  @Test
  public void tell() {
    try {
      client.tell(message);
    } catch (TellException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testReConnect() {
    try {
      client.reConnect();
    } catch (ConnectException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Test
  public void testIsAlive() throws Exception {
    client.isAlive();
  }

}
