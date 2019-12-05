package com.xiaodou.protocolnetty.netty.server;

import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.xiaodou.protocolnetty.proxy.SimpleServerProxy;
import com.xiaodou.protocolnetty.server.BasicServer;
import com.xiaodou.protocolnetty.util.ConcurrentHashSet;
import com.xiaodou.standard.protocol.MessageAble;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

@Sharable
public class GodInfoHandler extends ChannelDuplexHandler {
  
  private SimpleServerProxy proxyServer;
  
  private BasicServer server;
  
  private ChannelHandlerContext context;
  
  //构造方法
  public GodInfoHandler(SimpleServerProxy server, BasicServer basicServer){
    this.setProxyServer(server);
    this.server = basicServer;
  }
  
  //构造方法
  public GodInfoHandler(){}
  
  public SimpleServerProxy getProxyServer() {
    return proxyServer;
  }

  public void setProxyServer(SimpleServerProxy proxyServer) {
    this.proxyServer = proxyServer;
  }

  Logger logger = Logger.getLogger(GodInfoHandler.class);

  // 注册的所有channel
  public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  // 处于准备好状态的节点连接
  public static ConcurrentHashSet<Channel> readyList = new ConcurrentHashSet<Channel>();

  // 行分隔符
//  private static final String CR = System.getProperty("line.separator");
  
  @Override  
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt)  
          throws Exception {  
      // TODO Auto-generated method stub  
      super.userEventTriggered(ctx, evt);  

      if (evt instanceof IdleStateEvent) {  

          IdleStateEvent event = (IdleStateEvent) evt;  
            
          if (event.state().equals(IdleState.READER_IDLE)) {  
              //未进行读操作  
              System.out.println("READER_IDLE");  
              // 超时关闭channel  
               ctx.close();  

          } else if (event.state().equals(IdleState.WRITER_IDLE)) {  
                

          } else if (event.state().equals(IdleState.ALL_IDLE)) {  
              //未进行读写  
              System.out.println("ALL_IDLE");  
              // 发送心跳消息  
              
          }  
      }  
  }  
  

  /**
   * 注册触发事件
   */
  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    
    this.context =  ctx;
    channels.add(ctx.channel());
    System.out.println("新的连接建立" + ctx.channel());
    System.out.println("连接个数" + channels.size());
    ctx.fireChannelRegistered();
  }


  /**
   * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward to the next
   * {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelUnregistered();
  }


  /**
   * Calls {@link ChannelHandlerContext#connect(SocketAddress, SocketAddress, ChannelPromise)} to
   * forward to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
      SocketAddress localAddress, ChannelPromise promise) throws Exception {
    ctx.connect(remoteAddress, localAddress, promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#disconnect(ChannelPromise)} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    ctx.disconnect(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    ctx.close(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    ctx.deregister(promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#read()} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void read(ChannelHandlerContext ctx) throws Exception {

  }

  /**
   * Calls {@link ChannelHandlerContext#write(Object, ChannelPromise)} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    ctx.write(msg, promise);
  }

  /**
   * Calls {@link ChannelHandlerContext#flush()} to forward to the next
   * {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void flush(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel 读取完成...");
    ctx.flush();// 将缓冲区数据写入SocketChannel
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    channels.remove(ctx.channel());
    System.out.println("异常发生");
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    ByteBuf buf = (ByteBuf) msg;
    byte[] bytes = new byte[buf.readableBytes()];
    buf.readBytes(bytes);
    
    String message = new String(bytes, "UTF-8");
    System.out.println("服务端收到的消息： " + message);
    
    this.server.listen((MessageAble)msg);

//    if (message.startsWith("register")) {
//      handleRegister(ctx);
//    }
//    if (message.startsWith("ready")) {
//      handleReady(ctx);
//    }
  }

  // 处理注册事件
  public void handleRegister(ChannelHandlerContext ctx) {
    System.out.println("channels.size()" + channels.size());
    SocketAddress remoteAddress = ctx.channel().remoteAddress();
    System.out.println(remoteAddress);
    

    // 向客户端返回注册成功
    String response = "success registed ";

    ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
    ctx.channel().writeAndFlush(buffer);// 写入缓冲数组

    System.out.println(channels.size());

    for (Channel channel : channels) {
      System.out.println(channel.remoteAddress());
      ByteBuf buff = Unpooled.copiedBuffer(response.getBytes());//发送配置文件
      channel.writeAndFlush(buff);// 写入缓冲数组
    }
  }

  // 处理节点 上报 ready 事件
  public void handleReady(ChannelHandlerContext ctx) {
    // 向客户端写数据
    String response = "wait for start up";
    ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
    ctx.channel().writeAndFlush(buffer);// 写入缓冲数组
    readyList.add(ctx.channel());
    // 处于ready服务器的的数量等于配置文件服务器的数量
//    Map<Integer, QuorumServer> servers = PropertiesUtil.getGetPeerConfig().getServers();
//    if (servers.size() == readyList.size()) {
//      // 向全体服务器发送start消息
//      allReady(ctx);
//    }

  }

  // 所有节点ready事件
  public void allReady(ChannelHandlerContext ctx) {
    // 向客户端写数据
    String response = "start orgnize super network";
    for (Channel channel : channels) {
      ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
      channel.writeAndFlush(buffer);// 写入缓冲数组
    }
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
