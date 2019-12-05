package com.xiaodou.service;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

@Service("quesCacheService")
public class QuesCacheService {

  private static final FileUtil BASE_CACHE = FileUtil
      .getInstance("/conf/custom/env/base_cache.properties");

  private static final FileUtil AUTO_CACHE = FileUtil
      .getInstance("/conf/custom/env/auto_cache.properties");

  private static final String BASE_COMMON = "xiaodou";

  private static final String CACHE_PREFIX = ":";

  private static final String PROPERTY_PREFIX = ".";

  private String getCacheSecondKey(String className, String methodName) {
    StringBuilder keyBuilder = new StringBuilder(BASE_COMMON);
    _buildKey(keyBuilder, PROPERTY_PREFIX, className);
    _buildKey(keyBuilder, PROPERTY_PREFIX, methodName);
    return keyBuilder.toString();
  }

  public boolean isCached(String className, String methodName) {
    Boolean isCache = AUTO_CACHE.getPropertiesBool(getCacheSecondKey(className, methodName));
    return isCache != null && isCache;
  }

  public void cacheInfo(String className, String methodName, String param, Object value) {
    String cacheKey = getCacheKey(className, methodName, param);
    Integer cacheSeconds = BASE_CACHE.getPropertiesInt(getCacheSecondKey(className, methodName));
    JedisUtil.addStringToJedis(cacheKey, FastJsonUtil.toJson(value), cacheSeconds);
  }

  public String getCacheInfo(String className, String methodName, String param) {
    String cacheKey = getCacheKey(className, methodName, param);
    return JedisUtil.getStringFromJedis(cacheKey);
  }

  public void refreshCacheInfo(String className, String methodName, String param) {
    String cacheKey = getCacheKey(className, methodName, param);
    Integer cacheSeconds = BASE_CACHE.getPropertiesInt(getCacheSecondKey(className, methodName));
    JedisUtil.updateCacheSeconds(cacheKey, cacheSeconds);
  }

  private String getCacheKey(String className, String methodName, String param) {
    StringBuilder keyBuilder = new StringBuilder(BASE_COMMON);
    _buildKey(keyBuilder, CACHE_PREFIX, className);
    _buildKey(keyBuilder, CACHE_PREFIX, methodName);
    _buildKey(keyBuilder, CACHE_PREFIX, param);
    return keyBuilder.toString();
  }

  private StringBuilder _buildKey(StringBuilder builder, String prefix, String part) {
    return builder.append(prefix).append(part);
  }

}
