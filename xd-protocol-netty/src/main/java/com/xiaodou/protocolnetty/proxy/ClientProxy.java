package com.xiaodou.protocolnetty.proxy;

import org.apache.log4j.Logger;

import com.xiaodou.protocolnetty.netty.client.ClientInfoHandler;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.ShortClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @name ClientProxy CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月23日
 * @description client类的行为代理
 * @version 1.0
 */
public class ClientProxy extends ShortClient{

  Logger logger = Logger.getLogger(ClientProxy.class);

  private static ClientInfoHandler handler =  new ClientInfoHandler();
  private TargetSocket targetSocket;
  private EventLoopGroup group;
  private Bootstrap bootstrap;
  private ChannelFuture future;

  public ClientProxy(TargetSocket targetServer){
    super(targetServer);
    this.targetSocket = targetServer;
    try {
      connect0();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public <T extends MessageAble> boolean tell0(T message) throws Exception {
     ChannelFuture future = handler.sendMessage(message);
     return future.isSuccess();
  }

  public void connect0() throws Exception {
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
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
      logger.info("client 启动失败");
      e.printStackTrace();
    }
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
  public Integer usePort() {
   return this.targetSocket.getPort();
  }

}
