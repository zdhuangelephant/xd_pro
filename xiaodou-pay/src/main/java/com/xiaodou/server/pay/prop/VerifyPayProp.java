package com.xiaodou.server.pay.prop;

import com.xiaodou.common.util.FileUtil;

/* 获取支付购买凭证 */
public class VerifyPayProp {

  /* 配置文件 */
  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/verify_pay_tos.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (prop == null) synchronized (VerifyPayProp.class) {
      prop = FileUtil.getInstance("/conf/custom/env/verify_pay_tos.properties");
    }
    return prop;
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


}
