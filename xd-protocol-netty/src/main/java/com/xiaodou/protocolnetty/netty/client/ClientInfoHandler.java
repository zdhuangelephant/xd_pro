package com.xiaodou.protocolnetty.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class ClientInfoHandler extends ChannelDuplexHandler {

  private ByteBuf byteBuf;
  private ChannelHandlerContext context;

  public ClientInfoHandler(){}

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    
    this.context = ctx;
    // 向服务端发送数据
    byte[] bytes = "register".getBytes();
    byteBuf = Unpooled.buffer(bytes.length);
    byteBuf.writeBytes(bytes);// 写入buffer
    ctx.writeAndFlush(byteBuf);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    // 读取服务端发过来的数据
    ByteBuf buf = (ByteBuf) msg;
    byte[] bytes = new byte[buf.readableBytes()];
    buf.readBytes(bytes);
    String message = new String(bytes, "UTF-8");
    
    if (message.startsWith("success")) {
      // TODO 
      System.out.println("注册成功： " + message);
    }

    
    if (message.startsWith("config")) {
      // TODO 保存并且处理配置文件
      System.out.println("客户端收到config文件： " + message);
      // 向客户端写数据
      String response = "ready";
      ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
      ctx.writeAndFlush(buffer);// 写入缓冲数组
    }
    
    if (message.startsWith("wait")) {
      // TODO 系统可以启动，等待服务器下发命令
      System.out.println("进入待命状态： " + message);
    }

    System.out.println("客户端收到的消息： " + message);
  }
  
  /**
   * @param msg
   * @return 主动发送消息
   */
  public ChannelFuture sendMessage(Object msg){
    ChannelFuture writeAndFlush = context.writeAndFlush(msg);
    return writeAndFlush;
  }

}
