package com.xiaodou.node.paxos.netty.client;


import org.apache.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
  Logger logger = Logger.getLogger(NettyClient.class);

  public NettyClient(String host, int port) {
    this.host = host;
    this.port = port;
    connect();
  }

  String host;
  Integer port;
  EventLoopGroup group;
  Bootstrap bootstrap;

  public void connect() {

    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(group).channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {

          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ClientInfoHandler());
          }
        });
    ChannelFuture future;
    try {
      future = bootstrap.connect(host, port).sync();
    } catch (InterruptedException e) {
      logger.info("client 启动失败");
      e.printStackTrace();
    }
  }


  public static void main(String[] args) throws Exception {
    new NettyClient("localhost", 8888).connect();
  }

}
