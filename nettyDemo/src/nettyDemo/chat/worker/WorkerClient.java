package nettyDemo.chat.worker;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import nettyDemo.chat.decode.SendDecoder;
import nettyDemo.chat.encode.SendEncoder;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class WorkerClient {
	private final static int port=8999;
	ClientBootstrap bootstrap;
	WorkerClientHandler handler;
	ChannelFuture channelFuture;

	public void init() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		handler = new WorkerClientHandler();
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline channelPipeline = Channels.pipeline();
				channelPipeline.addLast("encode", new SendEncoder());
				channelPipeline.addLast("decode", new SendDecoder());
				channelPipeline.addLast("handler", handler);
				return channelPipeline;
			}
		});
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		bootstrap.setOption("reuseAddress", true);
	}

	public void start() {
//		channelFuture = bootstrap.connect(new InetSocketAddress("192.168.14.112",port));
	    channelFuture = bootstrap.connect(new InetSocketAddress(port));
		while(!channelFuture.isSuccess()){}
		System.out.println("连接远程服务器"+port+"端口成功，你现在可以开始发消息了。");
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
	
	public static void main(String args[]) {
	  int maxnum = Integer.parseInt(args[0]);
	  String message = "{\"action\":\"register\",\"subscribe\":[\"k0\",\"k1\",\"k2\"]}";
	  for(int i = 0; i < maxnum; i++){
	    WorkerClient chatClient = new WorkerClient();
	    chatClient.init();
	    chatClient.start();
	    
	    Channel channel = chatClient.getChannelFuture().getChannel();
	    channel.write(message);
	  }
    }
}