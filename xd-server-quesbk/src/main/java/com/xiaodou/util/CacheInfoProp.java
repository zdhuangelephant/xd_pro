package com.xiaodou.util;

import com.xiaodou.common.util.FileUtil;


public class CacheInfoProp {

  /**
   * 配置文件
   */
  private static FileUtil baseCache = FileUtil
      .getInstance("/conf/custom/env/base_cache.properties");
  private static FileUtil autoCache = FileUtil
      .getInstance("/conf/custom/env/auto_cache.properties");

  public static FileUtil getBaseCache() {
    return baseCache;
  }

  public static void setBaseCache(FileUtil baseCache) {
    CacheInfoProp.baseCache = baseCache;
  }

  public static FileUtil getAutoCache() {
    return autoCache;
  }

  public static void setAutoCache(FileUtil autoCache) {
    CacheInfoProp.autoCache = autoCache;
  }

}
