package com.elong.common.cache.local;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Response;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.IJedisPipeLineOperation;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.cache.redis.model.param.JedisKeyValuePair;
import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;

public class JedisUtilTest {

  // @Before
  public void beforeTest() {
    JedisUtil.addStringToJedis("zhaodan1", "zhaodan1value", 30000);
    JedisUtil.addStringToJedis("zhaodan2", "zhaodan2value", 30000);
  }

  // @Test
  public void testGroupGet() {
    List<JedisKeyValuePair> pairList = Lists.newArrayList();
    JedisKeyValuePair zhaodan1 = new JedisKeyValuePair("zhaodan1");
    pairList.add(zhaodan1);
    JedisKeyValuePair zhaodan2 = new JedisKeyValuePair("zhaodan2");
    pairList.add(zhaodan2);
    JedisUtil.getGroupStringFromJedis(pairList);
    System.out.println(zhaodan1.getValue());
    System.out.println(zhaodan2.getValue());
    Assert.assertEquals("zhaodan1 取值失败", "zhaodan1value", zhaodan1.getValue());
    Assert.assertEquals("zhaodan2 取值失败", "zhaodan2value", zhaodan2.getValue());
  }

  @Test
  public void testGroupOperation() {
    final Map<String, Response<?>> valMap = Maps.newHashMap();
    JedisUtil.groupOperationFromJedis(new IJedisPipeLineOperation() {
      @Override
      public void operationWithPipeLine(IPipeLineProxy<?> pipeLine) {
        valMap.put("val1", pipeLine.incr("zhaodan1"));
        valMap.put("val2", pipeLine.incr("zhaodan2"));
      }
    });
    System.out.println(valMap.get("val1").get());
    System.out.println(valMap.get("val2").get());
    Assert.assertEquals("zhaodan1 取值失败", 1l, valMap.get("val1").get());
    Assert.assertEquals("zhaodan2 取值失败", 1l, valMap.get("val2").get());
  }

  @After
  public void clear() {
    JedisUtil.delKeyFromJedis("zhaodan1");
    JedisUtil.delKeyFromJedis("zhaodan2");
  }

}
