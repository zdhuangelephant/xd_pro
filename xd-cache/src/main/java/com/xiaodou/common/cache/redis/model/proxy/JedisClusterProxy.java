package com.xiaodou.common.cache.redis.model.proxy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;

import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;

public class JedisClusterProxy implements IJedisProxy<JedisCluster> {

  public JedisClusterProxy(JedisCluster jedis) {
    this(jedis, false);
  }

  public JedisClusterProxy(JedisCluster jedis, boolean broken) {
    this.jedis = jedis;
    this.broken = broken;
  }

  private JedisCluster jedis;

  private boolean broken;

  @Override
  public boolean isBroken() {
    return this.broken;
  }

  @Override
  public void setBroken(boolean broken) {
    this.broken = broken;
  }

  @Override
  public JedisCluster getJedis() {
    return this.jedis;
  }

  @Override
  public void setJedis(JedisCluster jedis) {
    this.jedis = jedis;
  }

  public boolean exists(String key) {
    return this.jedis.exists(key);
  }

  public Long del(String key) {
    return this.jedis.del(key);
  }

  public void rpush(String key, String aList) {
    this.jedis.rpush(key, aList);
  }

  public void hset(String key, String field, String value) {
    this.jedis.hset(key, field, value);
  }

  public void hincrBy(String key, String incrementField, long incrementValue) {
    this.jedis.hincrBy(key, incrementField, incrementValue);

  }

  public String get(String key) {
    return this.jedis.get(key);
  }

  public List<String> lrange(String key, int i, int j) {
    return this.jedis.lrange(key, i, j);
  }

  public Map<String, String> hgetAll(String key) {
    return this.jedis.hgetAll(key);
  }

  public String hget(String key, String field) {
    return this.jedis.hget(key, field);
  }

  public void hmset(String key, Map<String, String> map) {
    this.jedis.hmset(key, map);
  }

  public List<String> hmget(String key, String... field) {
    return this.jedis.hmget(key, field);
  }

  @Override
  public String getSet(String key, String value) {
    return this.jedis.getSet(key, value);
  }

  @Override
  public void expire(String key, int cacheSeconds) {
    this.jedis.expire(key, cacheSeconds);
  }

  @Override
  public IPipeLineProxy<Pipeline> pipelined() {
    return null;
  }

  @Override
  public void set(byte[] bytes, byte[] byteArray) {
    try {
      this.jedis.set(new String(bytes, "utf-8"), new String(byteArray, "utf-8"));
    } catch (UnsupportedEncodingException e) {}
  }

  @Override
  public void expire(byte[] bytes, int cacheSeconds) {
    try {
      this.jedis.expire(new String(bytes, "utf-8"), cacheSeconds);
    } catch (UnsupportedEncodingException e) {}
  }

  @Override
  public byte[] get(byte[] bytes) {
    try {
      return this.jedis.get(new String(bytes, "utf-8")).getBytes();
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

  @Override
  public Long hdel(String key, String... fields) {
    return this.jedis.hdel(key, fields);
  }

  @Override
  public Long zadd(String key, double score, String member) {
    // TODO Auto-generated method stub
    return this.jedis.zadd(key, score, member);
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers) {
    // TODO Auto-generated method stub
    return this.jedis.zadd(key, scoreMembers);
  }

  @Override
  public Set<String> zrange(String key, long start, long end) {
    // TODO Auto-generated method stub
    return this.jedis.zrange(key, start, end);
  }

  @Override
  public Long zcard(String key) {
    // TODO Auto-generated method stub
    return this.jedis.zcard(key);
  }

  @Override
  public Long zrem(String key, String... member) {
    // TODO Auto-generated method stub
    return this.jedis.zrem(key, member);
  }

  /**
   * 自增1 (方法说明描述)
   * 
   * @param key
   * @return
   * 
   * @see com.elong.common.cache.redis.model.proxy.IJedisProxy#incr(java.lang.String)
   */
  @Override
  public Long incr(String key) {
    return this.jedis.incr(key);
  }

  @Override
  public boolean setnx(String key) {
    return this.jedis.setnx(key, LOCK) == 1;
  }

  @Override
  public IPipeLineProxy<?> getPipeLine() {
    throw new RuntimeException("Unsupport Action.");
  }

}
