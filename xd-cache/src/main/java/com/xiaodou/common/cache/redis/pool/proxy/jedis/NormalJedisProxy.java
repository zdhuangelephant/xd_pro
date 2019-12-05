package com.xiaodou.common.cache.redis.pool.proxy.jedis;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import com.google.common.collect.Sets;
import com.xiaodou.common.cache.redis.OPeration;
import com.xiaodou.common.cache.redis.model.proxy.JedisProxy;
import com.xiaodou.common.cache.redis.pool.JedisRandomConnectionPool;
import com.xiaodou.common.cache.redis.pool.proxy.IBasePoolProxy;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 分布式集群RedisPoolProxy
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月5日
 */
public class NormalJedisProxy implements IBasePoolProxy<JedisProxy> {
  
private JedisRandomConnectionPool jedisPool;
  
  public NormalJedisProxy(GenericObjectPoolConfig poolConfig, Integer timeOut, String hostIp) {
    Set<HostAndPort> jedisClusterNodes = getJedisInfoList(hostIp, timeOut);
    jedisPool = new JedisRandomConnectionPool(jedisClusterNodes, poolConfig, timeOut);
  }

  private Set<HostAndPort> getJedisInfoList(String hostIp, Integer timeOut) {
    Set<HostAndPort> jedisClusterNodes = Sets.newHashSet();
    String[] ips = hostIp.split(";");
    for (String ip : ips) {
      String[] host = ip.split(",");
      if (host.length < 2) continue;
      jedisClusterNodes.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
    }
    return jedisClusterNodes;
  }

  @Override
  public JedisProxy getResource() {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getConnection();
    } catch (Exception e) {
      LoggerUtil.error("Err : get resource failed.", e);
      jedisPool.returnBrokenConnection(jedis);
    }
    return new JedisProxy(jedis);
  }

  @Override
  public void returnBrokenResource(JedisProxy jedis) {
    jedisPool.returnBrokenConnection(jedis.getJedis());
  }

  @Override
  public void returnResource(JedisProxy jedis) {
    jedisPool.returnConnection(jedis.getJedis());
  }

  @Override
  public void destroy() {
    // @Todo destroy
  }

  @Override
  public void destroySlave() {
    this.destroy();
  }

  @Override
  public JedisProxy getSlaveResource() {
    return this.getResource();
  }

  @Override
  public void returnBrokenSlaveResource(JedisProxy jedis) {
    this.returnBrokenResource(jedis);
  }

  @Override
  public void returnSlaveResource(JedisProxy jedis) {
    this.returnResource(jedis);
  }

  @Override
  public JedisProxy getResource(OPeration operation) {
    return getResource();
  }
}
