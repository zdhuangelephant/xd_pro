package com.xiaodou.jredis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

public class ShardedJRedisPoolTest {

  @Test
  public void test() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxIdle(10);
    config.setMaxIdle(5 * 1000);
    config.setMaxWaitMillis(1 * 1000);

    int port1 = 6379;
    int port2 = 6380;

    List<JedisShardInfo> infos = new ArrayList<JedisShardInfo>();
    infos.add(new JedisShardInfo(SystemSetting.HOST_33, port1));
    infos.add(new JedisShardInfo(SystemSetting.HOST_33, port2));
    // infos.add(new JedisShardInfo(host, port5));
    // infos.add(new JedisShardInfo(host, port6));


    ShardedJRedisPool pool = new ShardedJRedisPool(config, infos);

    String key = "aa";
    String value = "aa";
    pool.getResource().set(key, value);
    System.out.println(pool.getResource().get(key));

    key = "bb";
    value = "bb";
    pool.getResource().set(key, value);
    System.out.println(pool.getResource().get(key));

    key = "cc";
    value = "cc";
    pool.getResource().set(key, value);
    System.out.println(pool.getResource().get(key));
  }
}
