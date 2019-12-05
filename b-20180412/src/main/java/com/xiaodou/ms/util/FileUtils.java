package com.xiaodou.ms.util;

import com.xiaodou.common.util.FileUtil;

/**
 * 读取配置文件
 * 
 * @author bing.cheng
 * 
 */
public class FileUtils {

  // csv文件列表
  public static FileUtil CSV = FileUtil.getInstance("/conf/custom/env/csvList.properties");

  // 读取含有推送次数的配置文件config.properties
  public static FileUtil CONFIG = FileUtil.getInstance("/conf/custom/env/config.properties");

}
