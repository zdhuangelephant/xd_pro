package com.xiaodou.im;

import com.xiaodou.common.util.FileUtil;

public class IMConfigManager {

  private static final String PROTOCOL = "im.protocol.%s";
  private static final String HOST = "im.host.%s";
  private static final String PORT = "im.port.%s";
  private static final String URL = "im.url.%s.%s";

  private static final String APP_NAME = "im.app.name.%s.%s";
  private static final String APP_KEY = "im.app.key.%s.%s";
  private static final String APP_ID = "im.app.id.%s.%s";
  private static final String APP_SECRET = "im.app.secret.%s.%s";


  /**
   * 配置文件
   */
  private static FileUtil baseProp = FileUtil
      .getInstance("/conf/custom/notenv/base_im_config.properties");
  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/im_config.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (prop == null) synchronized (IMConfigManager.class) {
      if (prop == null) prop = FileUtil.getInstance("/conf/custom/env/im_config.properties");
    }
    return prop;
  }

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getBaseProperty() {
    if (baseProp == null)
      synchronized (IMConfigManager.class) {
        if (baseProp == null)
          baseProp = FileUtil.getInstance("/conf/custom/notenv/base_im_config.properties");
      }
    return baseProp;
  }

  private static String getBaseParam(String format, Object... param) {
    return getBaseProperty().getProperties(String.format(format, param));
  }

  private static String getParam(String format, Object... param) {
    return getProperty().getProperties(String.format(format, param));
  }

  public static String getHost(String param) {
    return getBaseParam(HOST, param);
  }

  public static String getProtocol(String param) {
    return getBaseParam(PROTOCOL, param);
  }

  public static Integer getPort(String param) {
    return getBaseProperty().getPropertiesInt(String.format(PORT, param));
  }

  public static String getUrl(String param, String param2) {
    return getBaseParam(URL, param, param2);
  }

  public static String getAppName(String param, String module) {
    return getParam(APP_NAME, param, module);
  }

  public static String getAppKey(String param, String module) {
    return getParam(APP_KEY, param, module);
  }

  public static String getAppId(String param, String module) {
    return getParam(APP_ID, param, module);
  }

  public static String getAppSecret(String param, String module) {
    return getParam(APP_SECRET, param, module);
  }

}
