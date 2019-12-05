package com.xiaodou.node.paxos.netty.server;



import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
  Logger logger = Logger.getLogger(NettyServer.class);

  
  public NettyServer(Integer port, boolean isElectionPort) {
    this._port = port;
    this.isElectionPort = isElectionPort;
    init();
  }
  public NettyServer(String name, Integer port, boolean isElectionPort) {
    this._name = name;
    this._port = port;
    this.isElectionPort = isElectionPort;
    init();
  }
  
  private String _name;
  private int _port;
  private boolean isElectionPort;
  private ServerBootstrap bootstrap;
  private EventLoopGroup bossGroup ;
  private EventLoopGroup workerGroup;
  private ChannelDuplexHandler handler;

  public void init() {

      try {
        bootstrap = new ServerBootstrap();
        // 服务端辅助启动类，用以降低服务端的开发复杂度
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        handler =  new GodInfoHandler() ;

        bootstrap.group(bossGroup, workerGroup)
            // 实例化ServerSocketChannel
            .channel(NioServerSocketChannel.class)
            // 设置ServerSocketChannel的TCP参数
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {

              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handler);
                /*
                 * ch.pipeline().addLast("encode", new StringEncoder());
                 * ch.pipeline().addLast("decode", new StringDecoder());
                 */
              }
            });
        ChannelFuture f = bootstrap.bind(_port).sync();
        f.channel().closeFuture().sync();
        
      } catch (Exception e) {
        stop();
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

  public void stop() {
    workerGroup.shutdownGracefully();
    bossGroup.shutdownGracefully();
  }

  // private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
  // @Override
  // protected void initChannel(SocketChannel ch) throws Exception {
  // // handler
  // ch.pipeline().addLast(new ServerHandler());
  // }
  // }

  public static void main(String[] args) {
    int port = 8888;
    new NettyServer(port,true);
  }
}
