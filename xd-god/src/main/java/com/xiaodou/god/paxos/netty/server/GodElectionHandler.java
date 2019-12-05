package com.xiaodou.god.paxos.netty.server;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GodElectionHandler extends ChannelDuplexHandler{

  public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        
  /**
   * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward
   * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    for (Channel channel : channels) {
      channel.writeAndFlush("[SERVER] - " + ctx.channel().remoteAddress() + " 加入\n");
    }
    channels.add(ctx.channel());
    ctx.fireChannelRegistered();
  }


  /**
   * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward
   * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelUnregistered();
  }


  /**
   * Calls {@link ChannelHandlerContext#connect(SocketAddress, SocketAddress, ChannelPromise)} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                      SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.connect(remoteAddress, localAddress, promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#disconnect(ChannelPromise)} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
          throws Exception {
      ctx.disconnect(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.close(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.deregister(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#read()} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void read(ChannelHandlerContext ctx) throws Exception {
      
      ChannelHandlerContext read = ctx.read();
      System.out.println("read" + read.toString());
  }

  /**
   * Calls {@link ChannelHandlerContext#write(Object, ChannelPromise)} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      ctx.write(msg, promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#flush()} to forward
   * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void flush(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
  }
  
  @Override  
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    
      ByteBuf buf = (ByteBuf)msg;  
      byte[] bytes = new byte[buf.readableBytes()];  
      buf.readBytes(bytes);  
      String message = new String(bytes, "UTF-8");  
      System.out.println("服务端收到的消息： " + message);  
      String response;
      if ("register".equals(message)) {
          //向客户端写数据  
          response= "register ok";
      }
      else{
        response = "register fail";
      }
      ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());  
      ctx.write(buffer);//写入缓冲数组
  }  

  @Override  
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {     
      System.out.println("channel 读取完成...");  
      ctx.flush();//将缓冲区数据写入SocketChannel  
  }  

  @Override  
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
    
    System.out.println("exceptionCaught...");  
  }  
}
