package nettyDemo.chat.manager;

import org.jboss.netty.channel.Channel;

public class MessagePojo {
  
  private Channel channel;
  
  private String key;
  
  private String id;
  
  private String message;
  
  private static String callBackMessage = "{\"reply\":\"send ok\", \"id\": #param#}";

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCallBackMessage() {
    return callBackMessage.replaceAll("#param#", id);
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
