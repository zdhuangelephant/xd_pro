package com.xiaodou.common.test.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class FastJsonUtilTest {
  public static void main(String[] args) {
    Map<String, Object> map1 = Maps.newHashMap();
    map1.put("error", "true");
    System.out.println(FastJsonUtil.toJson(map1));
    Map<String, Object> map2 = new HashMap<>();
    map2.put("error", "true");
    System.out.println(FastJsonUtil.toJson(map2));
  }
}
