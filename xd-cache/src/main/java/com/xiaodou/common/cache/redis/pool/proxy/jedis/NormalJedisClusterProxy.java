package com.xiaodou.common.cache.redis.pool.proxy.jedis;

import java.util.Set;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.google.common.collect.Sets;
import com.xiaodou.common.cache.redis.OPeration;
import com.xiaodou.common.cache.redis.model.proxy.JedisClusterProxy;
import com.xiaodou.common.cache.redis.pool.proxy.IBasePoolProxy;

@Deprecated
public class NormalJedisClusterProxy implements IBasePoolProxy<JedisClusterProxy> {
  
  private JedisCluster jedisCluster;
  
  public NormalJedisClusterProxy(Config poolConfig, Integer timeOut, String hostIp) {
    Set<HostAndPort> jedisClusterNodes = getJedisInfoList(hostIp, timeOut);
    jedisCluster = new JedisCluster(jedisClusterNodes);
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
  public JedisClusterProxy getResource() {
    return new JedisClusterProxy(jedisCluster);
  }

  @Override
  public void destroy() {
  }

  @Override
  public void destroySlave() {
    this.destroy();
  }

  @Override
  public void returnBrokenResource(JedisClusterProxy jedis) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void returnResource(JedisClusterProxy jedis) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void returnBrokenSlaveResource(JedisClusterProxy jedis) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void returnSlaveResource(JedisClusterProxy jedis) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public JedisClusterProxy getResource(OPeration operation) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JedisClusterProxy getSlaveResource() {
    // TODO Auto-generated method stub
    return null;
  }
}
