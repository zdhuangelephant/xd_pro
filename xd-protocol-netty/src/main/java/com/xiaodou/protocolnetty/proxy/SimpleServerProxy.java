package com.xiaodou.protocolnetty.proxy;


import java.util.concurrent.TimeUnit;

import com.xiaodou.protocolnetty.netty.server.GodInfoHandler;
import com.xiaodou.protocolnetty.server.BasicServer;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.standard.protocol.abtract.SimpleServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @name SimpleServerProxy 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月23日
 * @description 服务端代理
 * @version 1.0
 */
public class SimpleServerProxy extends SimpleServer{

  private ServerBootstrap bootstrap = new ServerBootstrap();
  private EventLoopGroup bossGroup = new NioEventLoopGroup();
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private ChannelDuplexHandler handler ;

  
  
  private TargetSocket targetSocket;
  private ChannelFuture future;
  private BasicServer basicServer;
  
  public SimpleServerProxy(TargetSocket server , BasicServer basicServer){
    super(server);
    this.targetSocket = server;
    this.basicServer = basicServer;
  }
  
  public void connect0() {

      try {

        handler =  new GodInfoHandler(this,basicServer) ;
        bootstrap.group(bossGroup, workerGroup)
            // 实例化ServerSocketChannel
            .channel(NioServerSocketChannel.class)
            // 设置ServerSocketChannel的TCP参数
             .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {

              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handler);
                ch.pipeline().addLast("ping", new IdleStateHandler(25, 15, 10,TimeUnit.SECONDS));
                /*
                 * ch.pipeline().addLast("encode", new StringEncoder());
                 * ch.pipeline().addLast("decode", new StringDecoder());
                 */
              }
            });
        future = bootstrap.bind(targetSocket.getPort()).sync();
        System.out.println("重新连接成功");
        future.channel().closeFuture().sync();
        
      } catch (Exception e) {
        try {
          shutDown0();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        e.printStackTrace();
      }
    }
  

  
/*  //服务器绑定端口
  public void startup() {
    logger.info("服务器启动.   " + _name + " 端口:" + _port);
    try {
      ChannelFuture f = bootstrap.bind(_port).sync();
      f.channel().closeFuture().sync();

    } catch (InterruptedException e) {
      logger.info("服务器启动.   " + _name + " 端口:" + _port +"失败");
      e.printStackTrace();
    }
  }*/

  //TODO
  public <T extends MessageAble> boolean listen0(T message) throws Exception {
    return false;
  }

  public <T extends MessageAble> boolean tell0(T message) throws Exception {
    ChannelFuture future = ((GodInfoHandler) handler).sendMessage(message);
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
  public boolean shutDown0() throws Exception {
    boolean result = true;
    try {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public Integer usePort() {
    return this.targetSocket.getPort();
  }
 
}
