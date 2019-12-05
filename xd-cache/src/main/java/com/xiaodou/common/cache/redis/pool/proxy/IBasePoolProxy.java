package com.xiaodou.common.cache.redis.pool.proxy;

import com.xiaodou.common.cache.redis.OPeration;
import com.xiaodou.common.cache.redis.model.proxy.IJedisProxy;


/**
 * 基础抽象redis池代理,定义基本行为
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-4
 */
public interface IBasePoolProxy<T extends IJedisProxy<?>> {

  /**
   * 根据操作类型获取ShardedJedis实例
   * 
   * @param operation 操作类型
   * @return ShardedJedisProxy redis实例
   */
  public T getResource(OPeration operation);

  /**
   * 获取ShardedJedis-Master实例
   * 
   * @return ShardedJedisProxy redis实例
   */
  public T getResource();

  /**
   * 释放损坏的ShardedJedis实例
   * 
   * @param jedis ShardedJedis实例
   */
  public void returnBrokenResource(T jedis);

  /**
   * 释放 ShardedJedis实例
   * 
   * @param jedis ShardedJedis实例
   */
  public void returnResource(T jedis);

  /**
   * 销毁JedisPool
   */
  public void destroy();

  /**
   * 获取ShardedJedis-Slave实例
   * 
   * @return ShardedJedisProxy redis实例
   */
  public T getSlaveResource();

  /**
   * 释放损坏的ShardedJedis-Slave实例
   * 
   * @param jedis IJedisProxy-Slave实例
   */
  public void returnBrokenSlaveResource(T jedis);

  /**
   * 释放 IJedisProxy-Slave实例
   * 
   * @param jedis IJedisProxy-Slave实例
   */
  public void returnSlaveResource(T jedis);

  /**
   * 销毁JedisPool-Slave
   */
  public void destroySlave();

}
