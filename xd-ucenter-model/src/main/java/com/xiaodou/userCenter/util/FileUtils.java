package com.xiaodou.userCenter.util;

import com.xiaodou.common.util.FileUtil;

/**
 * 读取配置文件
 * 
 * @author bing.cheng
 * 
 */
public class FileUtils {
  /** 不需要登录，也能访问的url */
  private static FileUtil NO_LOGIN_ACCESS_FILTER_URL = FileUtil
      .getInstance("/conf/custom/env/access_url.properties");
  /** 用户中心相关参数 */
  private static FileUtil USER_CENTER_PROPERTIES = FileUtil
      .getInstance("/conf/custom/env/user_center.properties");

  public static FileUtil getNO_LOGIN_ACCESS_FILTER_URL() {
    return NO_LOGIN_ACCESS_FILTER_URL;
  }

  public static FileUtil getUSER_CENTER_PROPERTIES() {
    return USER_CENTER_PROPERTIES;
  }

}
