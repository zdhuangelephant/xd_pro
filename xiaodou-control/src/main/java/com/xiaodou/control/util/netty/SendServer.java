package com.xiaodou.control.util.netty;

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
 * @author <a href="zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @version 1.0
 * @date 2017年6月20日
 */
public class SendServer {
  public SendServer(){}
  private String _name="test";
  private int _port=12001;
  ServerBootstrap bootstrap;
  SendServerService handler = new SendServerService();
  public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_port() {
		return _port;
	}

	public void set_port(int _port) {
		this._port = _port;
	}
  public SendServer(String name, int port) {
    this._name = name;
    this._port = port;
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
	try{
		this.init();
		bootstrap.bind(new InetSocketAddress(_port));
		System.out.println("服务器启动.   " + _name + " 端口:" + _port);
	}catch(Exception e){
		System.out.print("服务器已经绑定端口"+_port);
	}
  }

  public void stop() {
    bootstrap.releaseExternalResources();
  }
}
