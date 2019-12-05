package nettyDemo.chat.server.recieve;

import java.util.List;

public class RegistPojo {
  
  private String action;
  
  private List<String> subscribe;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public List<String> getSubscribe() {
    return subscribe;
  }

  public void setSubscribe(List<String> subscribe) {
    this.subscribe = subscribe;
  }

}
