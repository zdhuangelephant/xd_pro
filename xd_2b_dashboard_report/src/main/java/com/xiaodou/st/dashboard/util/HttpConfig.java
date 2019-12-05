package com.xiaodou.st.dashboard.util;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;

public class HttpConfig {
  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil
      .getInstance("/conf/custom/env/http_config.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null)
      synchronized (ConfigProp.class) {
        if (config == null)
          config = FileUtil.getInstance("/conf/custom/env/http_config.properties");
      }
    return config;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  public static String quartzStudentUrl() {
    return getParams("quartz.quartzStudent");
  }
  
  public static String quartzApplyUrl() {
    return getParams("quartz.quartzApply");
  }

  public static String quartzAdmissionCardCodeUrl() {
    return getParams("quartz.admissionCardCode");
  }
}
