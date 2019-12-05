package nettyDemo.chat.manager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import nettyDemo.chat.util.ConcurrentHashSet;

import org.jboss.netty.channel.Channel;

public class KeyChannelMapping {
  
  private final static Boolean lock = Boolean.TRUE;
  
  private final static Map<String, ConcurrentHashSet<Channel>> subscribeKey_workerChannel_map=new ConcurrentHashMap<String, ConcurrentHashSet<Channel>>(); 
  
//  private final static Map<String, Integer> subscribeKey_workerChannelsize_map=new ConcurrentHashMap<String, Integer>();
  
  public static void removeChannel(Channel channel){
    Set<String> keys = subscribeKey_workerChannel_map.keySet();
    for(String key : keys){
      subscribeKey_workerChannel_map.get(key).remove(channel);
    }
  }
  
  public static Set<Channel> getChannelSet(String key){
    return subscribeKey_workerChannel_map.get(key);
  }
  
  public static int getChannelSetSize(String key){
    ConcurrentHashSet<Channel> concurrentHashSet = subscribeKey_workerChannel_map.get(key);
    if(null==concurrentHashSet)return 0;
      return concurrentHashSet.size();
  }
  
  public static void add(String key, Channel channelId){
    Set<Channel> set = subscribeKey_workerChannel_map.get(key);
    if(null!=set
        && set.size()>0){
      set.add(channelId);
      System.out.println("注册 key="+key+",channel="+channelId+",keySize="+set.size());
    }else if(null==set
        || set.size()==0){
      synchronized (lock) {
        initSet(key, channelId);
      }
    }
  }
  
  public static void initSet(String key, Channel channelId){
    Set<Channel> set = subscribeKey_workerChannel_map.get(key);
    if(set==null
        || set.size()==0){
      ConcurrentHashSet<Channel> channelList=new ConcurrentHashSet<Channel>();
      channelList.add(channelId);
      subscribeKey_workerChannel_map.put(key, channelList);
      System.out.println("注册 key="+key+",channel="+channelId+",keySize="+channelList.size());
    }else{
      set.add(channelId);
      System.out.println("注册 key="+key+",channel="+channelId+",keySize="+set.size());
    }
  }

}
