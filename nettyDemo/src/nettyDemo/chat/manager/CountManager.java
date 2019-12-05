package nettyDemo.chat.manager;

import java.util.Map;

public class CountManager implements Runnable {

  @Override
  public void run() {
    while(true){
      Map<String,Count> countMap = MessagePool.getCountMap();
      if(countMap.size() > 0){
        for(Count count : countMap.values()){
          count.validate();
        }
      }
    }
    
  }

}
