package com.xiaodou.node.paxos;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.xiaodou.node.paxos.PeerConfig.ConfigException;
import com.xiaodou.node.paxos.peer.SingleNode;

/**
 * @name NodeServer 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月5日
 * @description jar包启动以后创建服务
 * @version 1.0
 */
public class NodeServer implements Runnable
{

   /**
    * The server config
    */
   private PeerConfig serverConfig = null;

   /**
    * The zookeeper server (eventually part of a server 'quorum' that must be realized for
    * any service to stand up). All updates to zookeeper must be 'approved' by a quorum of
    * servers within the originally defined group.
    */
   private SingleNode server = null;

   private static Logger logger = Logger.getLogger(NodeServer.class);

   /**
    * Constructor.
    * 
    * @param configFile
    *           config file for the zookeeper server (server instance)
    * @throws ConfigException
    * @throws IOException
    */

   public NodeServer() 
   {
      try {
      } catch (Exception e) {
        serverConfig = PropertiesUtil.getGetPeerConfig();
        e.printStackTrace();
      }
   }

   @Override
   public void run()
   {
      try
      {
         if (serverConfig.isGod) {
//           server = new GodServer(serverConfig);
         }else{
//           server = new OrdinaryNode(serverConfig);
         }
         server.startServer();
         // wait for server thread to die
      }
      catch (Exception e)
      {
         server = null;
         logger.error("Server thread interrupted ...", e);
      }
   }

   /**
    * Returns the server config object
    * 
    * @return
    */
   public PeerConfig getServerConfig()
   {
      return serverConfig;
   }

}
