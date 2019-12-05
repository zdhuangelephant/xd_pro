package com.xiaodou.protocolnetty;


import org.junit.Test;

import com.xiaodou.protocolnetty.client.TempClient;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.exception.CommunicateException.ConnectException;
import com.xiaodou.standard.protocol.exception.TellException;

public class TestBasicClient {

  TargetSocket socket = new TargetSocket("localhost", 2018);

  TempClient client = new TempClient(socket);
  
  MessageAble message = new MessageAble() {
    
    @Override
    public String uniqueMessageName() {
      return "uniqueMessage";
    }
    
    @Override
    public TargetSocket to() {
      return new TargetSocket("localhost",2018);
    }
    
    @Override
    public String messageContent() {
      return "content";
    }
    
    @Override
    public TargetSocket from() {
      return new TargetSocket("localhost",2018);
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
  public void testgetUniqueId() {
    System.out.println("UniqueId + " + client.getUniqueId());
  }
  
  @Test
  public void testTell()  {
     try {
      boolean tell = client.tell(message);
      System.out.println("issuccess " + tell);
    } catch (TellException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void connect() throws ConnectException  {
    try {
      client.connect();
    } catch (Exception e) {
      client.reConnect();//连接失败以后尝试重新连接
      e.printStackTrace();
    }
  }

  @Test
  public void reConnect()  {
    try {
      client.reConnect();
    } catch (ConnectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test 
  public void isAlive0()  {
     try {
      client.isAlive0();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test 
  public void usePort() {
     System.out.println(client.getServer().getPort());
  }
}

