package com.xiaodou.oms.vo.input.sign;

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
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/oms_mapping.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ConfigProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/oms_mapping.properties");
    }
    return config;
  }


  private static String OMS_KEY = "oms.key_%param%";

  private static String REGEX = "%param%";

  public static String getKey(String productLine) {
    return getProperty().getProperties(OMS_KEY.replaceAll(REGEX, productLine));
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
