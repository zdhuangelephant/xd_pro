package nettyDemo.chat.server.send;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * Server端
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
public class SendServer {
  private String _name;
  private int _port;
  ServerBootstrap bootstrap;
  SendServerHandler handler = new SendServerHandler();
  
  public SendServer(String name, int port) {
    this._name = name;
    this._port = port;
    init();
  }

  private void init() {
    bootstrap =
        new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
          Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()*2)));
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("handler", handler);
        return pipeline;
      }
    });
    bootstrap.setOption("child.tcpNoDelay", true);
    bootstrap.setOption("child.keepAlive", true);
    bootstrap.setOption("reuseAddress", true);
  }

  public void startup() {
    bootstrap.bind(new InetSocketAddress(_port));
    System.out.println("服务器启动.   " + _name + " 端口:" + _port);
  }

  public void stop() {
    bootstrap.releaseExternalResources();
  }
}
