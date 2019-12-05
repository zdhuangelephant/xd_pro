package com.xiaodou.forum.util;

import com.xiaodou.common.util.FileUtil;

/**
 * 读取缓存过期时间
 * @author bing.cheng
 *
 */
public class CacheTimeProp {
  /**
   * 配置文件
   */
  private static FileUtil productInterfaceFile 
  			= FileUtil.getInstance("/conf/custom/env/cache_time.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (productInterfaceFile == null)
      synchronized (CacheTimeProp.class) {
        if (productInterfaceFile == null)
        	productInterfaceFile =  FileUtil.getInstance("/conf/custom/env/cache_time.properties");
      }
    return productInterfaceFile;
  }


  /**
   * 获取参数值
   * 
   * @param code 参数key
   *           
   * @return 参数值value
   */
  public static String getParams(String name) {
    return getProperty().getProperties(name);
  }
}
