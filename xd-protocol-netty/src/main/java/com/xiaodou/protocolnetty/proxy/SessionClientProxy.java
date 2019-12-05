package com.xiaodou.protocolnetty.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.xiaodou.protocolnetty.netty.client.ClientInfoHandler;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.SessionClient;
import com.xiaodou.standard.protocol.exception.ShutDownException;

/**
 * @name GodServerProxy 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月23日
 * @description 长连接的客户端
 * @version 1.0
 */
public class SessionClientProxy extends SessionClient{

  private static ClientInfoHandler handler =  new ClientInfoHandler();
  private TargetSocket targetSocket;
  private EventLoopGroup group = new NioEventLoopGroup();
  private Bootstrap bootstrap = new Bootstrap();
  
  private ChannelFuture future;
  
  public SessionClientProxy(TargetSocket server){
    super(server);
    this.targetSocket = server;
  }
  
  public void connect0() {

      try {
        
        bootstrap.group(group).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handler);
              }
            });
        try {
          future = bootstrap.connect(targetSocket.getHost(), targetSocket.getPort()).sync();
        } catch (InterruptedException e) {
          group.shutdownGracefully();// 关闭线程组
          System.out.println("client 启动失败");
          e.printStackTrace();
        }
        
      } catch (Exception e) {
        try {
          shutDown();
        } catch (ShutDownException e1) {
          e1.printStackTrace();
        }
        e.printStackTrace();
      }
    }

  //TODO
  public <T extends MessageAble> boolean listen0(T message) throws Exception {
    return false;
  }

  //双向引用，主动发送消息  
  public <T extends MessageAble> boolean tell0(T message) throws Exception {
    ChannelFuture future =  handler.sendMessage(message);
    return future.isSuccess();
  }
 
  public void reConnect0() throws Exception {
    if (null != future) {
      if (future.channel() != null && future.channel().isOpen()) {
        future.channel().close();
      }
    }
    System.out.println("准备重连");
    connect0();//重新连接
    System.out.println("重连成功");
  }
  
  public boolean isAlive0() throws Exception {
    return future.channel().isActive();
  }

  @Override
  public boolean heartBeat0() throws Exception {
    return isAlive0();
  }

  @Override
  public boolean shutDown0() throws Exception {
    boolean result = true;
    try {
      group.shutdownGracefully();
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public Integer usePort() {
    return targetSocket.getPort();
  }
 
}
