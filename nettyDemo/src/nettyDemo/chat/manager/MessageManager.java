package nettyDemo.chat.manager;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;

public class MessageManager implements Runnable {

  @Override
  public void run() {
    while (true) {
      if (null!=MessagePool.getMessage() && StringUtils.isNotBlank(MessagePool.getMessage().getMessage())) {
        writeMessage(MessagePool.getAndRemove());
      }
    }
  }

  public static void writeMessage(MessagePojo message) {
    try {
      
      Set<Channel> workerSet =
          KeyChannelMapping.getChannelSet(message.getKey());
//      if(null==workerSet||workerSet.isEmpty()||workerSet.size()==0){
//        RecoverService.recoverClient(message);
//      }
      Count count = new Count(message);
      MessagePool.registKeyCount(message.getId(), count);
      for (Channel channel : workerSet) {
        try {
          writeToWorker(message.getMessage(), channel, count);
        } catch (Exception e) {
          continue;
        }
      }
    } catch (Exception e) {
    }
  }
  
  private static void writeToWorker(String jsonString,Channel channel, final Count count){
    channel.write(jsonString);
//    channel.write(jsonString).addListener(new ChannelFutureListener(){
//      @Override
//      public void operationComplete(ChannelFuture future) throws Exception {
//        if (future.isDone()) {
//          count.down();
//        }
//      }
//    });;
  }

}
