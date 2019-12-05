package com.xiaodou.st.dashboard.util;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;

/**
 * @name @see com.xiaodou.st.dashboard.util.SyncLogUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月12日
 * @description 同步日志缓存工具类
 * @version 1.0
 */
public class SyncLogUtil {

  public static final Map<Short,SyncLogDO> lastSyncLogCacheMap = Maps.newConcurrentMap();
  public static final Map<Short,AtomicInteger> syncLogCountCacheMap = Maps.newConcurrentMap();
  
  private static final Map<String, AtomicInteger> cacheMap = Maps.newConcurrentMap();
  private static final String TOTAL_COUNT_KEY = "xd:moshare:synclog:totalcount:%s";
  private static final String COMPLETE_COUNT_KEY = "xd:moshare:synclog:completecount:%s";

   private static final String DEFAULT_COUNT = "0";

  public static String getTotalCount(String syncId) {
    //return JedisUtil.getStringFromJedis(totalKey(syncId));
    AtomicInteger ai = cacheMap.get(totalKey(syncId));
    return null != ai?Integer.toString(ai.get()):DEFAULT_COUNT;
  }

  public static String getCompleteCount(String syncId) {
    // return JedisUtil.getStringFromJedis(completeKey(syncId));
    AtomicInteger ai = cacheMap.get(completeKey(syncId));
    return null != ai?Integer.toString(ai.get()):DEFAULT_COUNT;
  }

  public static void setTotalCount(String syncId, Integer count) {
    if (null == count) return;
    cacheMap.put(totalKey(syncId), new AtomicInteger(count));
    cacheMap.put(completeKey(syncId), new AtomicInteger(0));
    // JedisUtil.addStringToJedis(totalKey(syncId), count.toString(), 86400);
  }

  public static void incrCompleteCount(String syncId) {
    cacheMap.get(completeKey(syncId)).incrementAndGet();
    //JedisUtil.incrKey(completeKey(syncId));
    // String count = JedisUtil.getStringFromJedis(completeKey(syncId));
    // if(StringUtils.isBlank(count)) count = DEFAULT_COUNT;
    // AtomicInteger ai = new AtomicInteger();
    // ai.set(Integer.valueOf(count) +1);
    // JedisUtil.addStringToJedis(completeKey(syncId), ai.get()+"", 86400);
  }

  public static void clear(String syncId) {
    cacheMap.remove(totalKey(syncId));
    cacheMap.remove(completeKey(syncId));
    //JedisUtil.delKeyFromJedis(totalKey(syncId));
    //JedisUtil.delKeyFromJedis(completeKey(syncId));
  }

  private static String totalKey(String syncId) {
    return String.format(TOTAL_COUNT_KEY, syncId);
  }

  private static String completeKey(String syncId) {
    return String.format(COMPLETE_COUNT_KEY, syncId);
  }

  
  public static int incrementAndGetExecuteCount(Short type){
    AtomicInteger ai = syncLogCountCacheMap.get(type);
    if(null == ai){
      ai = new AtomicInteger(0);
      syncLogCountCacheMap.put(type, ai);
    }
    return ai.incrementAndGet();
  }
  
  public static void clearExecuteCount(Short type){
    syncLogCountCacheMap.put(type, new AtomicInteger(0));
  }
  
}
