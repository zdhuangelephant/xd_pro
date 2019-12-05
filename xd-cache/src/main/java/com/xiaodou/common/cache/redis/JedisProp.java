package com.xiaodou.common.cache.redis;

import com.xiaodou.common.util.FileUtil;

/**
 * cache-config閰嶇疆鏂囦欢
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-4
 */
public class JedisProp {

  /**
   * 閰嶇疆鏂囦欢
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/cache.properties");

  /**
   * @return 鑾峰彇閰嶇疆鏂囦欢淇℃伅
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (JedisProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/cache.properties");
    }
    return config;
  }

  /**
   * 鑾峰彇鍙傛暟鍊�
   * 
   * @param code 鍙傛暟key
   * @return 鍙傛暟鍊�
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

}
