package nettyDemo.chat.worker;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;

public class WorkerSendTherd extends Thread {
  private WorkerClient workerClient;
  private String message;

  public static void main(String[] args) {
    String message = "{\"action\":\"register\",\"subscribe\":[\"a\",\"b\",\"c\"]}";
    if(StringUtils.isNotBlank(message)){
      WorkerSendTherd ster = new WorkerSendTherd(message);
      ster.start();
    }
  }
  
  private static byte[] getMessage(String message){
    int length = message.length();
    byte[] tomes = new byte[2];
    tomes[0] = (byte) (length & 0xff);
    tomes[1] = (byte) ((length >> 8) & 0xff);
    return tomes;
  }
  
  public WorkerSendTherd() {
    workerClient = new WorkerClient();
    workerClient.init();
    workerClient.start();
  }

  public WorkerSendTherd(String message2) {
    this.message = message2;
    workerClient = new WorkerClient();
    workerClient.init();
    workerClient.start();
  }

  public void run() {
    Channel channel = workerClient.getChannelFuture().getChannel();
    channel.write(message);
  }

}
