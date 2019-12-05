package com.xiaodou.jstorm2b;

import com.xiaodou.common.cache.redis.JedisUtil;

public class RedisTest {
  public static void main(String[] args) {
    JedisUtil.incrKey("zhaodan", 1800);
  }
}
