package nettyDemo.chat.server.send;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nettyDemo.chat.manager.MessagePojo;
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

public class SendServerHandler extends SimpleChannelHandler {
  private ChannelGroup channelGroup = new DefaultChannelGroup("client-channel-group");

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    Channel channel = e.getChannel();
    channel.close();
    if (channelGroup.contains(channel)) {
      channelGroup.remove(channel);
    }
  }

  @SuppressWarnings("unchecked")
  private MessagePojo setMessage(MessageEvent e) {
    String oldMessage = e.getMessage().toString();
    Map<String,Object> subScribeJsonMap =JSON.parseObject(oldMessage,HashMap.class);
    Set<String> keySet = subScribeJsonMap.keySet();
    MessagePojo pojo = new MessagePojo();
    pojo.setChannel(e.getChannel());
    for(String key : keySet){
      if("id".equals(key.toLowerCase())){
        pojo.setId(subScribeJsonMap.get(key).toString());
      }else if("action".equals(key.toLowerCase())){
        // TODO
      }else{
        pojo.setKey(key);
      }
    }
    pojo.setMessage(oldMessage);
    return pojo;
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    MessagePojo message = setMessage(e);
    if (null != message) {
      MessagePool.writeMessage(message);
    }
    channelGroup.remove(e.getChannel());
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    channelGroup.add(e.getChannel());
  }

  public ChannelGroup getChannelGroup() {
    return channelGroup;
  }
}
