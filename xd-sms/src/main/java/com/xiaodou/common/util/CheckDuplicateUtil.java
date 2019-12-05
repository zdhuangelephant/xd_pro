package com.xiaodou.common.util;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class CheckDuplicateUtil {

  public static boolean checkDuplicate(Object param, int time) {
    String key = getKey(param);
    if (JedisUtil.existKey(key)){
      return true;
    }
    JedisUtil.addStringToJedis(key, "1", time);
    return false;
  }

  private static String getKey(Object param) {
    try {
      return CommUtil.HEXAndMd5(FastJsonUtil.toJson(param));
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
    return StringUtils.EMPTY;
  }

}
