package com.xiaodou.common.cache.redis.model.proxy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;
import com.xiaodou.common.cache.redis.model.proxy.pipeline.ShardedPipeLineProxy;

/**
 * ShardedJedis实例代理
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-4-10
 */
public class ShardedJedisProxy implements IJedisProxy<ShardedJedis> {

  public ShardedJedisProxy(ShardedJedis jedis) {
    this(jedis, false);
  }

  public ShardedJedisProxy(ShardedJedis jedis, boolean broken) {
    this.jedis = jedis;
    this.broken = broken;
  }

  private ShardedJedis jedis;

  private boolean broken;

  public boolean isBroken() {
    return broken;
  }

  public void setBroken(boolean broken) {
    this.broken = broken;
  }

  public ShardedJedis getJedis() {
    return jedis;
  }

  public void setJedis(ShardedJedis jedis) {
    this.jedis = jedis;
  }

  public String getSet(String key, String value) {
    return this.jedis.getSet(key, value);
  }

  public void expire(String key, int cacheSeconds) {
    this.jedis.expire(key, cacheSeconds);
  }

  public IPipeLineProxy<ShardedJedisPipeline> pipelined() {
    return new ShardedPipeLineProxy(this.jedis.pipelined());
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

  public void set(byte[] bytes, byte[] byteArray) {
    this.jedis.set(bytes, byteArray);
  }

  public void expire(byte[] bytes, int cacheSeconds) {
    this.jedis.expire(bytes, cacheSeconds);
  }

  public byte[] get(byte[] bytes) {
    return this.jedis.get(bytes);
  }

  public void hmset(String key, Map<String, String> map) {
    this.jedis.hmset(key, map);
  }

  public List<String> hmget(String key, String... field) {
    return this.jedis.hmget(key, field);
  }

  @Override
  public Long hdel(String key, String... fields) {
    return this.jedis.hdel(key, fields);
  }

  @Override
  public Long zadd(String key, double score, String member) {
    return this.jedis.zadd(key, score, member);
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers) {
    return this.jedis.zadd(key, scoreMembers);
  }

  @Override
  public Set<String> zrange(String key, long start, long end) {
    return this.jedis.zrange(key, start, end);
  }

  @Override
  public Long zcard(String key) {
    return this.jedis.zcard(key);
  }

  @Override
  public Long zrem(String key, String... member) {
    // TODO Auto-generated method stub
    return this.jedis.zrem(key, member);
  }

  /**
   * 自增 (方法说明描述)
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
  public IPipeLineProxy<ShardedJedisPipeline> getPipeLine() {
    return new ShardedPipeLineProxy(this.jedis.pipelined());
  }
}
