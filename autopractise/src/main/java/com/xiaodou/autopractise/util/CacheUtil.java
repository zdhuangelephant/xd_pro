package com.xiaodou.autopractise.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class CacheUtil {

  private static Map<String, Long> timeMap = Maps.newConcurrentMap();

  public static boolean isWrong(String unique, String userId) {
    String key = String.format("%s-%s", unique, userId);
    if (timeMap.containsKey(key)) {
      Long now = System.currentTimeMillis();
      Long time = timeMap.get(key);
      timeMap.put(key, System.currentTimeMillis());
      if (now < time + 2500) {
        return true;
      } else {
        return false;
      }
    } else {
      timeMap.put(key, System.currentTimeMillis());
      return false;
    }
  }

}
