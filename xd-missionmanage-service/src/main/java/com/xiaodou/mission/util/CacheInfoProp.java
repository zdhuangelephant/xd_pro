package com.xiaodou.mission.util;

import com.xiaodou.common.util.FileUtil;


/**
 * @name @see com.xiaodou.mission.util.CacheInfoProp.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 缓存信息配置工具类
 * @version 1.0
 */
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
