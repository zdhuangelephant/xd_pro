package com.xiaodou.server.pay.web.sign;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;

/**
 * <p>
 * Key与商户号配置文件管理
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月14日
 */
public class SignMessConf {

  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/sign_message.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null)
      synchronized (ConfigProp.class) {
        if (config == null)
          config = FileUtil.getInstance("/conf/custom/env/sign_message.properties");
      }
    return config;
  }

  private static String SIGN_KEY = "sign.key_%s";

  public static String getKey(String merchantCode) {
    return getProperty().getProperties(String.format(SIGN_KEY, merchantCode));
  }

  /**
   * getValueByKey
   * 
   * @Title: getValueByKey
   * @Description: 根据key获得value
   * @param key
   * @return
   */
  public static String getValueByKey(String key) {
    return getProperty().getProperties(key);
  }

}
