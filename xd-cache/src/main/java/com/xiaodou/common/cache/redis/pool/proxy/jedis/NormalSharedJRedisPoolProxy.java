package com.xiaodou.common.cache.redis.pool.proxy.jedis;

import java.util.List;

import com.google.common.collect.Lists;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisShardInfo;

import com.xiaodou.jredis.ShardedJRedisPool;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.cache.redis.OPeration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.xiaodou.common.cache.redis.pool.proxy.IBasePoolProxy;
import com.xiaodou.common.cache.redis.model.proxy.JedisCommondsProxy;

/**
 * 一致性哈希ShardedRedisPoolProxy
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月5日
 */
public class NormalSharedJRedisPoolProxy implements IBasePoolProxy<JedisCommondsProxy> {

  private static ShardedJRedisPool jedisPool;

  public NormalSharedJRedisPoolProxy(GenericObjectPoolConfig poolConfig, Integer timeOut,
      String hostIp) {
    List<JedisShardInfo> lst = getJedisInfoList(hostIp, timeOut);
    jedisPool = new ShardedJRedisPool(poolConfig, lst);
  }

  private List<JedisShardInfo> getJedisInfoList(String hostIp, Integer timeOut) {
    List<JedisShardInfo> lst = Lists.newArrayList();
    String[] ips = hostIp.split(";");
    for (String ip : ips) {
      String[] host = ip.split(",");
      if (host.length < 3) continue;
      JedisShardInfo jedisInfo =
          new JedisShardInfo(host[0], Integer.parseInt(host[1]), timeOut, host[2]);
      if (host.length == 4 && StringUtils.isNotBlank(host[3])) jedisInfo.setPassword(host[3]);
      lst.add(jedisInfo);
    }
    return lst;
  }

  @Override
  public JedisCommondsProxy getResource() {
    JedisCommands jedis = null;
    try {
      jedis = jedisPool.getResource();
    } catch (Exception e) {
      LoggerUtil.error("Err : get resource failed.", e);
      jedisPool.returnBrokenResource(jedis);
    }
    return new JedisCommondsProxy(jedis);
  }

  @Override
  public void returnBrokenResource(JedisCommondsProxy jedis) {
    jedisPool.returnBrokenResource(jedis.getJedis());
  }

  @Override
  public void returnResource(JedisCommondsProxy jedis) {
    jedisPool.returnResource(jedis.getJedis());
  }

  @Override
  public void destroy() {
    jedisPool.destroy();
  }

  @Override
  public void destroySlave() {
    this.destroy();
  }

  @Override
  public JedisCommondsProxy getSlaveResource() {
    return this.getResource();
  }

  @Override
  public void returnBrokenSlaveResource(JedisCommondsProxy jedis) {
    this.returnBrokenResource(jedis);
  }

  @Override
  public void returnSlaveResource(JedisCommondsProxy jedis) {
    this.returnResource(jedis);
  }

  @Override
  public JedisCommondsProxy getResource(OPeration operation) {
    return getResource();
  }

}
