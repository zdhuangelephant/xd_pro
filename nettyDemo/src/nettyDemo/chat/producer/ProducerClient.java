package nettyDemo.chat.producer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class ProducerClient {
  private final static int port = 8899;
  ClientBootstrap bootstrap;
  ProducerClientHandler handler;
  ChannelFuture channelFuture;

  public void init() {
    bootstrap =
        new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool()));
    handler = new ProducerClientHandler(this);
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("encode", new StringEncoder());
        channelPipeline.addLast("decode", new StringDecoder());
        channelPipeline.addLast("handler", handler);
        return channelPipeline;
      }
    });
    bootstrap.setOption("tcpNoDelay", true);
    bootstrap.setOption("keepAlive", false);
    bootstrap.setOption("reuseAddress", true);
  }

  public void start(String message) {
//    channelFuture = bootstrap.connect(new InetSocketAddress("192.168.14.112",port));
    channelFuture = bootstrap.connect(new InetSocketAddress(port));
    Channel channel = channelFuture.getChannel();
    while(!channelFuture.isSuccess()){}
    channel.write(message);
//    while(!channel.isConnected()){}
//    channel.disconnect();
  }


  public void stop() {
    channelFuture.awaitUninterruptibly();
    if (!channelFuture.isSuccess()) {
      channelFuture.getCause().printStackTrace();
    }
    channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
    bootstrap.releaseExternalResources();
  }

  public ClientBootstrap getBootstrap() {
    return bootstrap;
  }

  public ChannelFuture getChannelFuture() {
    return channelFuture;
  }
}
