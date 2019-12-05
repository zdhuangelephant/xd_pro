package com.xiaodou.common.cache.redis.model.proxy.pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * Pipeline代理类
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月4日
 */
public class PipeLineProxy implements IPipeLineProxy<Pipeline> {

  private Pipeline pipeLine;

  public PipeLineProxy(Pipeline pipelined) {
    this.pipeLine = pipelined;
  }

  @Override
  public void setex(String key, int cacheSeconds, String value) {
    this.pipeLine.setex(key, cacheSeconds, value);
  }

  @Override
  public void set(String key, String value) {
    this.pipeLine.set(key, value);
  }

  @Override
  public Response<String> get(String key) {
    return this.pipeLine.get(key);
  }

  @Override
  public void sync() {
    this.pipeLine.sync();
  }

  @Override
  public void expire(String key, int cacheSeconds) {
    this.pipeLine.expire(key, cacheSeconds);
  }

  @Override
  public Response<Boolean> exists(String key) {
    return this.pipeLine.exists(key);
  }

  @Override
  public Response<Long> del(String key) {
    return this.pipeLine.del(key);
  }

  @Override
  public void rpush(String key, String aList) {
    this.pipeLine.rpush(key, aList);
  }

  @Override
  public void hset(String key, String field, String value) {
    this.pipeLine.hset(key, field, value);
  }

  @Override
  public void hincrBy(String key, String incrementField, long incrementValue) {
    this.pipeLine.hincrBy(key, incrementField, incrementValue);
  }

  @Override
  public Response<List<String>> lrange(String key, int i, int j) {
    return this.pipeLine.lrange(key, i, j);
  }

  @Override
  public Response<Map<String, String>> hgetAll(String key) {
    return this.pipeLine.hgetAll(key);
  }

  @Override
  public Response<String> hget(String key, String field) {
    return this.hget(key, field);
  }

  @Override
  public void set(byte[] bytes, byte[] byteArray) {
    this.pipeLine.set(bytes, byteArray);
  }

  @Override
  public void expire(byte[] bytes, int cacheSeconds) {
    this.pipeLine.expire(bytes, cacheSeconds);
  }

  @Override
  public Response<byte[]> get(byte[] bytes) {
    return this.pipeLine.get(bytes);
  }

  @Override
  public void hmset(String key, Map<String, String> map) {
    this.pipeLine.hmset(key, map);
  }

  @Override
  public Response<List<String>> hmget(String key, String... field) {
    return this.pipeLine.hmget(key, field);
  }

  @Override
  public Response<Long> hdel(String key, String... fields) {
    return this.pipeLine.hdel(key, fields);
  }

  @Override
  public Response<Long> setnx(String key) {
    return this.pipeLine.setnx(key, LOCK);
  }

  @Override
  public Response<Long> incr(String key) {
    return this.pipeLine.incr(key);
  }

  @Override
  public Response<Long> zadd(String key, double score, String member) {
    return this.pipeLine.zadd(key, score, member);
  }

  @Override
  public Response<Long> zadd(String key, Map<String, Double> scoreMembers) {
    return this.pipeLine.zadd(key, scoreMembers);
  }

  @Override
  public Response<Set<String>> zrange(String key, long start, long end) {
    return this.pipeLine.zrange(key, start, end);
  }

  @Override
  public Response<Long> zcard(String key) {
    return this.pipeLine.zcard(key);
  }

  @Override
  public Response<Long> zrem(String key, String... member) {
    return this.pipeLine.zrem(key, member);
  }

}
