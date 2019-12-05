package com.xiaodou.god.paxos;

import org.apache.log4j.Logger;

import com.xiaodou.god.paxos.peer.OrdinaryNode;


/**
 * @name StartUp 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月5日
 * @description 在main方法执行,启动excuteable jar包以后执行的一系列操作
 * @version 1.0
 */
public class StartUpNode {
  
  public static void main(String[] args) {
  
    Logger logger = Logger.getLogger(StartUpNode.class);
    logger.info("--------------------运行jar包，尝试启动server-------------------");
    
    try {
      OrdinaryNode server = new OrdinaryNode();
      Thread thread = new Thread(server);
      thread.start();
    } catch (IllegalArgumentException e) {
      logger.info("--------------------配置文件参数错误-------------------");
      logger.info( e.getMessage());
    }
  }
}
