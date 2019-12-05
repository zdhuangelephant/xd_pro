package com.xiaodou.common.cache.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xiaodou.common.cache.redis.model.proxy.IJedisProxy;
import com.xiaodou.common.cache.redis.pool.proxy.IBasePoolProxy;
import com.xiaodou.common.util.log.LoggerUtil;



/**
 * Redis实例工厂
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-20
 */
@SuppressWarnings("rawtypes")
public class JedisConf {

  /**
   * JedisPoolProxy单例
   */
  private static volatile IBasePoolProxy jedisPool;

  /**
   * No-Use
   */
  private JedisConf() {}

  /**
   * 初始化实例方法(依托于Spring框架管理RedisPoolBean,如有需要请重写此工厂类)
   * 
   * @throws Exception
   */
  private static synchronized void init() throws Exception {
    try {
      if (jedisPool == null) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/conf/core/cache.xml");
        jedisPool = (IBasePoolProxy) context.getBean(JedisProp.getParams("jedisBean"));
      }
    } catch (Exception e) {
      LoggerUtil.error("Err : get jedisPool failed.", e);
      throw e;
    }
  }

  /**
   * 默认获取读写实例
   * 
   * @return Redis实例
   * @throws Exception 异常
   */
  public static IJedisProxy<?> getInstance() {
    return getInstance(OPeration.READWRITE);
  }

  /**
   * 根据操作类型获取实例
   * 
   * @return Redis实例
   * @throws Exception 异常
   */
  public static IJedisProxy<?> getInstance(OPeration operation) {
    try {
      if (null == jedisPool) {
        synchronized (JedisConf.class) {
          if (null == jedisPool) init();
        }
      }
      return jedisPool.getResource(operation);
    } catch (Exception e) {
      LoggerUtil.error("Err : get jedisPool failed.", e);
      return null;
    }
  }

  /**
   * 释放Redis实例
   * 
   * @param jedis redis实例
   */
  @SuppressWarnings("unchecked")
  public static void release(IJedisProxy jedis) {
    if (jedis != null) {
      if (jedis.isBroken()) {
        jedisPool.returnBrokenResource(jedis);
        jedis.setJedis(null);
        jedis = null;
      } else {
        jedisPool.returnResource(jedis);
        jedis.setJedis(null);
        jedis = null;
      }
    }
  }

}
