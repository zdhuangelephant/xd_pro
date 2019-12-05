package com.xiaodou.userCenter.util;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.userCenter.common.Constant;

/**
 * @name @see com.xiaodou.userCenter.util.CheckDuplicateUtil.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月10日
 * @description 判重
 * @version 1.0
 */
public class CheckDuplicateUtil {

  public static boolean checkDuplicate(String param, int time) {
    if(StringUtils.isEmpty(param)) {
      return false;
    }
    String key = Constant.DUPLICATE_USER + getKey(param);
    if (JedisUtil.existKey(key)) {
      return true;
    }
    JedisUtil.addStringToJedis(key, param, time);
    return false;
  }

  private static String getKey(String param) {
    try {
      return CommUtil.HEXAndMd5(param);
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
    return StringUtils.EMPTY;
  }

}
