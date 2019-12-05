package com.xiaodou.oms.agent.util;

import com.xiaodou.common.util.FileUtil;

/**
 * Created by zyp on 14-7-1.
 */
public class OrderConfigService {
  /**
   * 配置文件
   */
  private static FileUtil confFile = FileUtil.getInstance("/conf/custom/env/oms.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (confFile == null) synchronized (OrderConfigService.class) {
      if (confFile == null) confFile = FileUtil.getInstance("/conf/custom/env/oms.properties");
    }
    return confFile;
  }

  private static String OMS_KEY = "oms.key_%s";

  public static String getKey(String productLine) {
    return getProperty().getProperties(String.format(OMS_KEY, productLine));
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

  /**
   * reload
   * 
   * @Title: reload
   * @Description: 重新加载
   */
  public static void reload() {
    confFile = FileUtil.getInstance("/conf/custom/env/oms.properties");
  }
}
