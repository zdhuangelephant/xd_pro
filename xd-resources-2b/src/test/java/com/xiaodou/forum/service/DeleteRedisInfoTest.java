package com.xiaodou.forum.service;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.constant.forum.Constant;

public class DeleteRedisInfoTest extends BaseUnitils {
  /**
   * 查看测试环境的redis剩余时间（话题分类）
   */
  @Test
  public void showSYTime(){
    Jedis jedis = new Jedis("211.157.137.249", 6379);
    System.out.println(jedis.ttl(Constant.FORUM + Constant.COMMON_DELIMITER + Constant.FORUM_CATEGORY));
    jedis.close();
  }
}
