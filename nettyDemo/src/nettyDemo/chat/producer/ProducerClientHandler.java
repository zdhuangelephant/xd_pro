package nettyDemo.chat.producer;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ProducerClientHandler extends SimpleChannelHandler {

  public ProducerClientHandler(ProducerClient client) {
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    String content = (String) e.getMessage();
    System.out.println(content);
    e.getChannel().disconnect();
    e.getChannel().close();
//    System.exit(-1);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    e.getCause().printStackTrace();
    Channel channel = e.getChannel();
    channel.close();
  }

}
