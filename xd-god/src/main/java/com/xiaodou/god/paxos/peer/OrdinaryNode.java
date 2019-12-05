package com.xiaodou.god.paxos.peer;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.xiaodou.god.paxos.PropertiesUtil;
import com.xiaodou.god.paxos.netty.client.NettyClient;

/**
 * @name NormalServer CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月7日
 * @description TODO
 * @version 1.0
 */
public class OrdinaryNode  implements Runnable{

  Logger logger = Logger.getLogger(OrdinaryNode.class);
  //extends SingleNode
  
//  public OrdinaryNode(PeerConfig serverConfig) {
//    super(serverConfig);
//  }
//
//  
//  private String serverAdress;
//  private String port;
//
//  @Override
//  public void startServer() {
//    try {
//      Properties godServerInfo = PropertiesUtil.getGodServerInfo();
//      serverAdress = godServerInfo.getProperty("server");
//      port = godServerInfo.getProperty("port");
//      
//      NettyClient client = new NettyClient(serverAdress, Integer.valueOf(port));
//      client.connect();
//
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    Properties godServerInfo = PropertiesUtil.getGodServerInfo();
    String  serverAdress = godServerInfo.getProperty("server");
    String port = godServerInfo.getProperty("port");
    
    NettyClient client = new NettyClient(serverAdress, Integer.valueOf(port));
    client.connect();
  }
}
