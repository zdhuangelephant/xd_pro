package com.xiaodou.node.paxos.peer;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.xiaodou.node.paxos.PropertiesUtil;
import com.xiaodou.node.paxos.netty.server.NettyServer;

/**
 * @name GodServer 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月7日
 * @description TODO
 * @version 1.0
 */
public class GodServer  implements Runnable{

  //extends SingleNode
    Logger logger = Logger.getLogger(GodServer.class);
  
  
//  public GodServer(PeerConfig serverConfig) {
//    super(serverConfig);
//  }
//
//  Logger logger = Logger.getLogger(GodServer.class);
//  
//  @Override
//  public void startServer(){
//    
//    
//    logger.info("开始启动godserver");
//    Properties godInfo = PropertiesUtil.getGodServerInfo();
//    //election server
//    final Integer godServerPort = (Integer) godInfo.get("port");
//
//    if (godServerPort>0) {
//      //启动election 的server
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          NettyServer electionServer = new NettyServer(godServerPort,true);
//          logger.info("start godserver" + godServerPort);
//          electionServer.startup();
//        }
//      }).start();
//    }
//    
//  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    
    logger.info("开始启动godserver");
    Properties godInfo = PropertiesUtil.getGodServerInfo();
    //election server
    final String godServerPort = godInfo.getProperty("port");
    NettyServer electionServer = new NettyServer(Integer.valueOf(godServerPort),true);
    logger.info("start godserver" + godServerPort);
//    electionServer.startup();
  }
}
