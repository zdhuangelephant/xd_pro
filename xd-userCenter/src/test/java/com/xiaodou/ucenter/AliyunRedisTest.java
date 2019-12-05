package com.xiaodou.ucenter;

import org.junit.Test;

import com.xiaodou.common.cache.redis.JedisUtil;

public class AliyunRedisTest extends BaseUnitils {

  @Test
  public void testSetAndGet() {
    JedisUtil.addStringToJedis("zhaodan", "zhaodan", 0);
    assert "zhaodan".equals(JedisUtil.getStringFromJedis("zhaodan"));
  }

}
