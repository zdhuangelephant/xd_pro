package com.xiaodou.st.dashboard.prop;

import com.xiaodou.common.util.FileUtil;

/**
 * 
 * @author 李德洪
 * 
 */
public class WxPayProp {

  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/wxPay_config.properties");

  private static FileUtil getProperty() {
    if (null == prop) {
      synchronized (WxPayProp.class) {
        prop = FileUtil.getInstance("/conf/custom/env/wxPay_config.properties");
      }
    }
    return prop;
  }

  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }
}
