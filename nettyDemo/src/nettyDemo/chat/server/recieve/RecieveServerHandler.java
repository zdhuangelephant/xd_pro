package nettyDemo.chat.server.recieve;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nettyDemo.chat.manager.KeyChannelMapping;
import nettyDemo.chat.manager.MessagePool;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import com.alibaba.fastjson.JSON;

public class RecieveServerHandler extends SimpleChannelHandler {
  private static ChannelGroup channelGroup = new DefaultChannelGroup("client-channel-group");
  
  public static void setChannelGroup(ChannelGroup channelGroup) {
    RecieveServerHandler.channelGroup = channelGroup;
  }

  private static String message = "{\"reply\":\"register ok\"}";
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    Channel channel = e.getChannel();
    channel.close();
    KeyChannelMapping.removeChannel(channel);
    channel=null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    Map<String, Object> subScribepojo=JSON.parseObject(e.getMessage().toString(), HashMap.class);
    if("register".equals(subScribepojo.get("action"))){
      for(String subscribekey : (List<String>)subScribepojo.get("subscribe")){
        KeyChannelMapping.add(subscribekey, e.getChannel());
      }
//      channelGroup.remove(e.getChannel());
      e.getChannel().write(message);
    }
    if("receive ok".equals(subScribepojo.get("reply"))){
      MessagePool.keyDown(subScribepojo.get("id").toString());
    }
    subScribepojo = null;
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    channelGroup.add(e.getChannel());
  }

  public static ChannelGroup getChannelGroup() {
    return channelGroup;
  }

}
