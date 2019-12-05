package nettyDemo.chat.manager;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessagePool {
  
  private static Queue<MessagePojo> message = new ConcurrentLinkedQueue<MessagePojo>();
  
  private static Map<String, Count> countMap = new ConcurrentHashMap<String, Count>();
  
  public static Map<String, Count> getCountMap() {
    return countMap;
  }

  public static void setCountMap(Map<String, Count> countMap) {
    MessagePool.countMap = countMap;
  }

  public static void writeMessage(MessagePojo mess){
    message.add(mess);
  }
  
  public static MessagePojo getMessage(){
    if(message.isEmpty())return null;
    return message.peek();
  }
  
  public static MessagePojo getAndRemove(){
    if(message.isEmpty())return null;
    return message.poll();
  }
  
  public static void registKeyCount(String key, Count count){
    countMap.put(key, count);
  }
  
  public static void keyDown(String key){
    Count count = countMap.get(key);
    if(null!=count){
      count.down();
    }
  }
  
  public static void deleteKey(String key){
    countMap.remove(key);
  }
  
  public static boolean hasKey(String key){
    return countMap.containsKey(key);
  }

}
