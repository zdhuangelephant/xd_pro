package com.xiaodou.common.cache.redis.pool.proxy.jedis;

import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.cache.redis.JedisProp;
import com.xiaodou.common.cache.redis.OPeration;
import com.xiaodou.common.cache.redis.model.proxy.ShardedJedisProxy;
import com.xiaodou.common.cache.redis.pool.proxy.IBasePoolProxy;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * M/S集群ShardedRedisPoolProxy
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月5日
 */
public class MasterSlaveSharedJedisPoolProxy implements IBasePoolProxy<ShardedJedisProxy> {

  /**
   * JedisPool-Master
   */
  private static ShardedJedisPool jedisMasterPool;

  /**
   * JedisPool-Slave
   */
  private static ShardedJedisPool jedisSlavePool;

  /**
   * Alis-List
   */
  private Set<String> nameSet;

  /**
   * Constractor
   * 
   * @param poolConfig config
   * @param timeOut 超时
   * @param masterHostIp MasterIp
   * @param slaveHostIp SlaveIp
   */
  public MasterSlaveSharedJedisPoolProxy(GenericObjectPoolConfig poolConfig, Integer timeOut,
      String hostIp) {

    nameSet = Sets.newHashSet();
    String hostIps[] = hostIp.split(JedisProp.getParams("redis.separator"));

    // 初始化主实例组
    List<JedisShardInfo> masterLst = getMasterJedisInfoList(hostIps[0], timeOut);
    jedisMasterPool = new ShardedJedisPool(poolConfig, masterLst);

    // 初始化从实例组
    List<JedisShardInfo> slaveLst = getSlaveJedisInfoList(hostIps[1], timeOut);
    jedisSlavePool = new ShardedJedisPool(poolConfig, slaveLst);
  }

  private List<JedisShardInfo> getMasterJedisInfoList(String hostIp, Integer timeOut) {
    List<JedisShardInfo> lst = Lists.newArrayList();
    String[] ips = hostIp.split(";");
    for (String ip : ips) {
      String[] host = ip.split(",");
      if (host.length < 3) continue;
      if (nameSet.add(host[2])) {
        JedisShardInfo jedisInfo =
            new JedisShardInfo(host[0], Integer.parseInt(host[1]), timeOut, host[2]);
        if (host.length == 4 && StringUtils.isNotBlank(host[3])) jedisInfo.setPassword(host[3]);
        lst.add(jedisInfo);
      } else {
        throw new RuntimeException("The host name is repeat");
      }
    }
    return lst;
  }

  private List<JedisShardInfo> getSlaveJedisInfoList(String hostIp, Integer timeOut) {
    List<JedisShardInfo> lst = Lists.newArrayList();
    String[] ips = hostIp.split(";");
    for (String ip : ips) {
      String[] host = ip.split(",");
      if (host.length < 3) continue;
      if (nameSet.remove(host[2])) {
        lst.add(new JedisShardInfo(host[0], Integer.parseInt(host[1]), timeOut, host[2]));
      } else {
        throw new RuntimeException("Unknown slave name that can't match to a exits master");
      }
      if (!nameSet.isEmpty()) throw new RuntimeException("Count of slave can't match the master");
    }
    return lst;
  }

  @Override
  public void destroy() {
    jedisMasterPool.destroy();
  }

  @Override
  public void destroySlave() {
    jedisSlavePool.destroy();
  }

  @Override
  public ShardedJedisProxy getResource() {
    ShardedJedis jedis = null;
    try {
      jedis = jedisMasterPool.getResource();
    } catch (Exception e) {
      LoggerUtil.error("Err : get resource failed.", e);
      jedisMasterPool.returnBrokenResource(jedis);
    }
    return new ShardedJedisProxy(jedis);
  }

  @Override
  public ShardedJedisProxy getSlaveResource() {
    ShardedJedis jedis = null;
    try {
      jedis = jedisSlavePool.getResource();
    } catch (Exception e) {
      jedisSlavePool.returnBrokenResource(jedis);
      LoggerUtil.error("Err : get resource failed.", e);
    }
    return new ShardedJedisProxy(jedis);
  }

  @Override
  public void returnBrokenResource(ShardedJedisProxy jedis) {
    jedisMasterPool.returnBrokenResource(jedis.getJedis());
  }

  @Override
  public void returnBrokenSlaveResource(ShardedJedisProxy jedis) {
    jedisSlavePool.returnBrokenResource(jedis.getJedis());
  }

  @Override
  public void returnResource(ShardedJedisProxy jedis) {
    jedisMasterPool.returnResource(jedis.getJedis());
  }

  @Override
  public void returnSlaveResource(ShardedJedisProxy jedis) {
    jedisSlavePool.returnResource(jedis.getJedis());
  }

  @Override
  public ShardedJedisProxy getResource(OPeration operation) {
    switch (operation) {
      case READ:
        return getSlaveResource();
      case READWRITE:
        return getResource();
      case WRITE:
        return getResource();
      default:
        return getResource();
    }
  }

}
