package com.xiaodou.sms.utils;

import com.xiaodou.common.util.FileUtil;

/**
 * 
 * 获取sms_merchant_info.properties文件内容工具类
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class SmsMerchantInfoProp {
  /**
   * 配置文件
   */
  private static FileUtil productInterfaceFile = FileUtil
      .getInstance("/conf/custom/env/sms_merchant_info.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (productInterfaceFile == null)
      synchronized (SmsMerchantInfoProp.class) {
        if (productInterfaceFile == null)
          productInterfaceFile =
              FileUtil.getInstance("/conf/custom/env/sms_merchant_info.properties");
      }
    return productInterfaceFile;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * 
   * @return 参数值value
   */
  public static String getParams(String name) {
    return getProperty().getProperties(name);
  }
  
  /**
   * 获取参数值
   * 
   * @param code 参数key
   * 
   * @return 参数值value
   */
  public static Integer getInteger(String name) {
    return getProperty().getPropertiesInt(name);
  }
}
