package com.xiaodou.common.cache.local;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * @name @see com.xiaodou.common.cache.local.DynamicTimingLocalCache.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 本地动态计时缓存
 * @version 1.0
 */
public class DynamicTimingLocalCache {

  private static int size = 10000;

  private static void check() {
    if (localCacheListMap.size() > size) {
      SummerCommonScheduledExecutor.getExecutor().schedule(new SummerScheduledTask() {
        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("循环检查异常.", t);
        }

        @Override
        public void doMain() {
          for (CacheObject object : localCacheListMap.values()) {
            object.check();
          }
        }
      }, 0, TimeUnit.SECONDS);
    }
  }

  private static Map<String, CacheObject> localCacheListMap = Maps.newConcurrentMap();

  /**
   * 获取缓存
   * 
   * @param localCacheKey key
   * @return value 值
   */
  @SuppressWarnings("unchecked")
  public static <T> T getCache(String localCacheKey) {
    if (localCacheListMap.containsKey(localCacheKey)) {
      CacheObject cacheObject = localCacheListMap.get(localCacheKey);
      if (null != cacheObject && null != cacheObject.getValue()) return (T) cacheObject.getValue();
    }
    return null;
  }

  /**
   * 缓存对象
   * 
   * @param localCacheKey key
   * @param value 值
   * @param lifeCycle 生命周期(单位:秒)
   */
  public static void cache(String localCacheKey, Object value, long lifeCycle) {
    localCacheListMap.put(localCacheKey, new CacheObject(localCacheKey, value, lifeCycle));
    check();
  }

  /**
   * @name @see com.xiaodou.common.cache.local.CacheObject.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月8日
   * @description 计时缓存对象
   * @version 1.0
   */
  public static class CacheObject {

    private Object release() {
      localCacheListMap.remove(_key);
      this._key = null;
      this._releaseTime = null;
      this._value = null;
      return null;
    }

    public void check() {
      if (System.currentTimeMillis() > _releaseTime) release();
    }

    public CacheObject(String key, Object value, long lifeCycle) {
      if (0 != lifeCycle)
        _releaseTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(lifeCycle);
      _key = key;
      _value = value;
    }

    private Long _releaseTime = Long.MAX_VALUE;

    private String _key;

    private Object _value;

    public Object getValue() {
      if (System.currentTimeMillis() < _releaseTime) return _value;
      return release();
    }
  }

}
