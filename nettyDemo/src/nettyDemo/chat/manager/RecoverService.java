package nettyDemo.chat.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.Channel;

public class RecoverService {

  private static ExecutorService pool = Executors.newCachedThreadPool();

  public static void recoverClient(MessagePojo e) {
    pool.execute(new RecoverTask(e));
  }
}


class RecoverTask implements Runnable {
  public RecoverTask(MessagePojo e) {
    this.e = e;
  }

  private MessagePojo e;

  private void recover() {
    Channel channel = e.getChannel();
    try {
      if(channel.isConnected()){
        channel.write(e.getCallBackMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      e=null;
    }
  }

  @Override
  public void run() {
    this.recover();
  }

}
