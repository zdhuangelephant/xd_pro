package nettyDemo.chat.manager;

import java.util.concurrent.atomic.AtomicInteger;

public class Count{
  public AtomicInteger _count;
  private String _key;
  
  public int getCount() {
    return _count.get();
  }

  public String getKey() {
    return _id;
  }

  public void setKey(String key) {
    this._id = key;
  }

  private String _id;
  
  private MessagePojo e;
  
  public MessagePojo getE() {
    return e;
  }
  
  public void setE(MessagePojo e) {
    this.e = e;
  }
  
  public Count(MessagePojo message){
    this._count = new AtomicInteger(0);
    this.e = message;
    this._id = message.getId();
    this._key = message.getKey();
  }
  
  public void down(){
    _count.incrementAndGet();
  }
  
  public void validate(){
    int channelSetSize = KeyChannelMapping.getChannelSetSize(_key);
    if(channelSetSize<=_count.get()){
      recover();
    }
  }
  
  private synchronized void recover(){
    if(MessagePool.hasKey(_id)){
      RecoverService.recoverClient(e);
      MessagePool.deleteKey(_id);
    }
  }
  
}