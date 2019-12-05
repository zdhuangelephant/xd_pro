package com.xiaodou.common.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Response;

import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.model.param.JedisKeyValuePair;
import com.xiaodou.common.cache.redis.model.proxy.IJedisProxy;
import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * Jedis操作类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-24
 */
public class JedisUtil {

  public static boolean getLock(String key, int cacheSeconds) {
    if (StringUtils.isBlank(key)) return false;
    IJedisProxy<?> jedis = null;
    Boolean lastVal = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      lastVal = jedis.setnx(key);
      if (cacheSeconds != 0) {
        jedis.expire(key, cacheSeconds);
      } else {
        jedis.expire(key, 300);
      }
      if (lastVal)
        LoggerUtil.cacheInfo("Redis : getLock success. key = " + key);
      else
        LoggerUtil.cacheInfo("Redis : getLock failed. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getLock error. key = " + key);
      return false;
    } finally {
      JedisConf.release(jedis);
    }
    return lastVal;
  }

  /**
   * 将key的值递增1
   * 
   * @param key
   * @return
   */
  public static Long incrKey(String key) {
    return incrKey(key, 0);
  }

  /**
   * 将key的值递增1
   * 
   * @param key
   * @return
   */
  public static Long incrKey(String key, int cacheSeconds) {
    if (StringUtils.isBlank(key)) return null;
    IJedisProxy<?> jedis = null;
    Long lastVal = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      lastVal = jedis.incr(key);
      if (cacheSeconds != 0) {
        jedis.expire(key, cacheSeconds);
      }
      LoggerUtil.cacheInfo("Redis : addStringToJedis success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addStringToJedis failed. key = " + key);
      return null;
    } finally {
      JedisConf.release(jedis);
    }
    return lastVal;
  }

  /**
   * 缓存字符串
   * 
   * @param key key
   * @param value value
   * @param cacheSeconds 缓存时间
   * @return 缓存结果
   * @throws Exception
   */
  public static String addStringToJedis(String key, String value, int cacheSeconds) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) return null;
    IJedisProxy<?> jedis = null;
    String lastVal = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      lastVal = jedis.getSet(key, value);
      if (cacheSeconds != 0) {
        jedis.expire(key, cacheSeconds);
      }
      LoggerUtil.cacheInfo("Redis : addStringToJedis success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addStringToJedis failed. key = " + key);
      return null;
    } finally {
      JedisConf.release(jedis);
    }
    return lastVal;
  }

  /**
   * 缓存字符串
   * 
   * @param batchData value
   * @param cacheSeconds 缓存时间
   * @return 缓存结果
   * @throws Exception
   */
  public static void addStringToJedis(Map<String, String> batchData, int cacheSeconds) {
    if (batchData.isEmpty() || batchData.size() == 0) return;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      IPipeLineProxy<?> pipeline = jedis.pipelined();
      for (Map.Entry<String, String> element : batchData.entrySet()) {
        if (cacheSeconds != 0) {
          pipeline.setex(element.getKey(), cacheSeconds, element.getValue());
        } else {
          pipeline.set(element.getKey(), element.getValue());
        }
      }
      pipeline.sync();
      LoggerUtil.cacheInfo("Redis : addStringToJedis success. <Map>");
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addStringToJedis failed. <Map>");
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 缓存List
   * 
   * @param key key
   * @param list value
   * @param cacheSeconds 缓存时间
   * @return 缓存结果
   * @throws Exception
   */
  public static void addListToJedis(String key, List<String> list, int cacheSeconds) {
    if (StringUtils.isBlank(key)) return;
    if (list != null && list.size() > 0) {
      IJedisProxy<?> jedis = null;
      try {
        jedis = JedisConf.getInstance(OPeration.WRITE);
        if (jedis.exists(key)) {
          jedis.del(key);
        }
        for (String aList : list) {
          jedis.rpush(key, aList);
        }
        if (cacheSeconds != 0) {
          jedis.expire(key, cacheSeconds);
        }
        LoggerUtil.cacheInfo("Redis : addListToJedis success. key = " + key);
      } catch (Exception e) {
        if (null != jedis) jedis.setBroken(true);
        LoggerUtil.cacheInfo("Redis : addListToJedis failed. key = " + key);
      } finally {
        JedisConf.release(jedis);
      }
    }
  }

  /**
   * 缓存HashMap
   * 
   * @param key key
   * @param field field
   * @param value value
   * @param cacheSeconds 缓存时间
   * @throws Exception
   */
  public static void addHashMapToJedis(String key, String field, String value, int cacheSeconds) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || StringUtils.isBlank(value))
      return;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      if (jedis != null) {
        jedis.hset(key, field, value);
        if (cacheSeconds != 0) {
          jedis.expire(key, cacheSeconds);
        }
      }
      LoggerUtil.cacheInfo("Redis : addHashMapToJedis success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addHashMapToJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 更新HashMap
   * 
   * @param key key
   * @param incrementField incrementField
   * @param incrementValue incrementValue
   * @param dateField dateField
   * @param dateValue dateValue
   * @throws Exception
   */
  public static void updateHashMapToJedis(String key, String incrementField, long incrementValue,
      String dateField, String dateValue) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(incrementField)
        || StringUtils.isBlank(dateField) || StringUtils.isBlank(dateValue)) return;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      jedis.hincrBy(key, incrementField, incrementValue);
      jedis.hset(key, dateField, dateValue);
      LoggerUtil.cacheInfo("Redis : updateHashMapToJedis success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : updateHashMapToJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 获取缓存字符串
   * 
   * @param key key
   * @return value
   * @throws Exception
   */
  public static String getStringFromJedis(String key) {
    if (StringUtils.isBlank(key)) return null;
    String value = null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis.exists(key)) {
        value = jedis.get(key);
        value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
      }
      LoggerUtil.cacheInfo("Redis : getStringFromJedis hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getStringFromJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
    return value;
  }

  /**
   * 获取缓存List
   * 
   * @param key key
   * @return list
   * @throws Exception
   */
  public static List<String> getListFromJedis(String key) {
    if (StringUtils.isBlank(key)) return null;
    List<String> list = null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis.exists(key)) {
        list = jedis.lrange(key, 0, -1);
      }
      LoggerUtil.cacheInfo("Redis : getListFromJedis hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getListFromJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
    return list;
  }

  /**
   * 获取HahMap制定域
   * 
   * @param key key
   * @param field fieldname
   * @return value
   * @throws Exception
   */
  public static String getHashMapValueFromJedis(String key, String field) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) return null;
    String value = null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis != null) {
        if (jedis.exists(key)) {
          value = jedis.hget(key, field);
        }
      }
      LoggerUtil.cacheInfo("Redis : getHashMapValueFromJedis hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getHashMapValueFromJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
    return value;
  }

  /**
   * 删除某db的某个key值
   * 
   * @param key key
   * @return delResult
   * @throws Exception
   */
  public static Long delKeyFromJedis(String key) {
    if (StringUtils.isBlank(key)) return null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      Long del = jedis.del(key);
      LoggerUtil.cacheInfo("Redis : delKeyFromJedis success. key = " + key);
      return del;
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : delKeyFromJedis failed. key = " + key);
      return null;
    } finally {
      JedisConf.release(jedis);
    }

  }

  /**
   * 是否存在key
   * 
   * @param key key
   * @return boolean
   * @throws Exception
   */
  public static boolean existKey(String key) {
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      Boolean exists = jedis.exists(key);
      LoggerUtil.cacheInfo("Redis : existKey success. key = " + key);
      return exists;
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : existKey failed. key = " + key);
      return false;
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 缓存对象
   * 
   * @param key key
   * @param object object
   * @param cacheSeconds 缓存时间
   * @throws Exception
   */
  public static void addObject(String key, Serializable object, int cacheSeconds) {
    IJedisProxy<?> jedis = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      oos = new ObjectOutputStream(bos);
      oos.writeObject(object);
      jedis.set(key.getBytes(), bos.toByteArray());
      if (cacheSeconds != 0) {
        jedis.expire(key.getBytes(), cacheSeconds);
      }
      LoggerUtil.cacheInfo("Redis : addObject success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addObject failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
      try {
        if (null != oos) oos.close();
      } catch (Exception e) {}
    }
  }

  /**
   * 获取缓存对象
   * 
   * @param key key
   * @return object
   * @throws Exception
   */
  public static Object getObject(final String key) {
    if (null == key) return null;
    IJedisProxy<?> jedis = null;
    Object object = null;
    ObjectInputStream ois = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      ByteArrayInputStream bais = new ByteArrayInputStream(jedis.get(key.getBytes()));
      ois = new ObjectInputStream(bais);
      object = ois.readObject();
      LoggerUtil.cacheInfo("Redis : getObject hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getObject failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
      try {
        if (null != ois) ois.close();
      } catch (Exception e) {}
    }
    return object;
  }

  /**
   * 缓存HashMap
   * 
   * @param key key
   * @param map map
   * @param cacheSeconds 缓存时间
   * @throws Exception
   */
  public static void addHashMapToJedis(String key, Map<String, String> map, int cacheSeconds) {
    if (StringUtils.isBlank(key) || map.isEmpty() || map.size() == 0) return;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      if (jedis != null) {
        jedis.hmset(key, map);
        if (cacheSeconds != 0) {
          jedis.expire(key, cacheSeconds);
        }
      }
      LoggerUtil.cacheInfo("Redis : addHashMapToJedis success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : addHashMapToJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 获取HahMap全部值
   * 
   * @param key key
   * @return value
   * @throws Exception
   */
  public static Map<String, String> getAllMapValueFromJedis(String key) {
    if (StringUtils.isBlank(key)) return null;
    Map<String, String> value = null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis != null) {
        if (jedis.exists(key)) {
          value = jedis.hgetAll(key);
        }
      }
      LoggerUtil.cacheInfo("Redis : getAllMapValueFromJedis hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getAllMapValueFromJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
    return value;
  }

  /**
   * 获取HahMap制定域
   * 
   * @param key key
   * @param field fieldname
   * @return value
   * @throws Exception
   */
  public static List<String> getMapListValueFromJedis(String key, String field) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) return null;
    List<String> value = null;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis != null) {
        if (jedis.exists(key)) {
          value = jedis.hmget(key, field);
        }
      }
      LoggerUtil.cacheInfo("Redis : getMapListValueFromJedis hit. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getMapListValueFromJedis failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
    return value;
  }

  /**
   * 更新缓存时间
   * 
   * @param key key
   * @param cacheSeconds 缓存时间
   */
  public static void updateCacheSeconds(String key, int cacheSeconds) {
    if (StringUtils.isBlank(key)) return;
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.WRITE);
      if (cacheSeconds != 0) {
        jedis.expire(key, cacheSeconds);
      }
      LoggerUtil.cacheInfo("Redis : updateCacheSeconds success. key = " + key);
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : updateCacheSeconds failed. key = " + key);
    } finally {
      JedisConf.release(jedis);
    }
  }

  /**
   * 获取一组key的值
   * 
   * @param keyValuePairList
   */
  public static <T extends JedisKeyValuePair> void getGroupStringFromJedis(List<T> keyValuePairList) {
    if (null == keyValuePairList || keyValuePairList.size() == 0) return;
    IJedisProxy<?> jedis = null;
    Map<String, Response<String>> jedisResponseMap = Maps.newHashMap();
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis != null) {
        IPipeLineProxy<?> pipeLine = jedis.getPipeLine();
        if (pipeLine != null) {
          for (T keyValuePair : keyValuePairList) {
            String key = keyValuePair.getKey();
            if (StringUtils.isBlank(key)) continue;
            Response<String> value = pipeLine.get(key);
            jedisResponseMap.put(key, value);
          }
          pipeLine.sync();
          for (T keyValuePair : keyValuePairList) {
            String key = keyValuePair.getKey();
            if (StringUtils.isBlank(key)) continue;
            Response<String> value = jedisResponseMap.get(key);
            if (null == value) continue;
            keyValuePair.setValue(value.get());
          }
        }
      }
      LoggerUtil.cacheInfo("Redis : getGroupStringFromJedis hit.");
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getMapListValueFromJedis failed.");
    } finally {
      JedisConf.release(jedis);
    }
  }

  public static void groupOperationFromJedis(IJedisPipeLineOperation operation) {
    IJedisProxy<?> jedis = null;
    try {
      jedis = JedisConf.getInstance(OPeration.READ);
      if (jedis != null) {
        IPipeLineProxy<?> pipeLine = jedis.getPipeLine();
        if (pipeLine != null) {
          operation.operationWithPipeLine(pipeLine);
          pipeLine.sync();
        }
      }
      LoggerUtil.cacheInfo("Redis : getGroupStringFromJedis hit.");
    } catch (Exception e) {
      if (null != jedis) jedis.setBroken(true);
      LoggerUtil.cacheInfo("Redis : getMapListValueFromJedis failed.");
    } finally {
      JedisConf.release(jedis);
    }
  }
}
