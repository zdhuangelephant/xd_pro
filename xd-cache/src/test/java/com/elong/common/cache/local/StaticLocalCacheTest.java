package com.elong.common.cache.local;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.xiaodou.common.cache.local.StaticLocalCache;

public class StaticLocalCacheTest {
  
  @Test
  public void testGetMap(){
    Map<String, Object> maps = Maps.newHashMap();
    maps.put("123", "123");
    StaticLocalCache.initLocalCacheMap(maps);
    assert StaticLocalCache.getLocalCacheMap("123").equals("123");
  }

}
