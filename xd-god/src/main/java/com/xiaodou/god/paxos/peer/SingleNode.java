package com.xiaodou.god.paxos.peer;

import com.xiaodou.god.paxos.PeerConfig;

/**
 * 定义一个抽象节点
 * 
 * */
public abstract class SingleNode  {
  
  public SingleNode(PeerConfig serverConfig){
    this.serverConfig = serverConfig;
  }
  
  public PeerConfig serverConfig;
  
  public void setServerConfig(PeerConfig serverConfig){
    this.serverConfig = serverConfig;
  }
  
  public PeerConfig getServerConfig(){
    return serverConfig;
  }
  
  public abstract void startServer();
}
