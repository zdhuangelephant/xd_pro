package com.xiaodou.server.pay.prop;

import com.xiaodou.common.util.FileUtil;

/**
 * 
 * @author 李德洪
 * 
 */
public class AliPayProp {

  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/aliPay_config.properties");

  private static FileUtil getProperty() {
    if (null == prop) {
      synchronized (AliPayProp.class) {
        prop = FileUtil.getInstance("/conf/custom/env/aliPay_config.properties");
      }
    }
    return prop;
  }

  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }
}
