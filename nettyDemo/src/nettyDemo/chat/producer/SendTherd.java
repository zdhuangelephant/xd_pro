package nettyDemo.chat.producer;

import org.apache.commons.lang.StringUtils;

public class SendTherd extends Thread {
  private String message;

  public static void main(String[] args) {
//    int maxnum = Integer.parseInt(args[0]);
    String message = "{\"action\":\"send\",\"k#key#\":\"k0-0\",\"id\": #param#}";
    if (StringUtils.isNotBlank(message)) {
      for(int i=0;i<1;i++){
        String messages = message.replaceAll("#key#", Integer.toString(i));
        new SendTherd(messages).start();
      }
    }
  }

  public SendTherd() {
    this(null);
  }

  public SendTherd(String message2) {
    this.message = message2;
  }

  public void run() {
    ProducerClient chatClient = new ProducerClient();
    for(int i=0;i<5;i++){
      chatClient.init();
      chatClient.start(message.replaceAll("#param#", Integer.toString(i)));
    }
  }

}
