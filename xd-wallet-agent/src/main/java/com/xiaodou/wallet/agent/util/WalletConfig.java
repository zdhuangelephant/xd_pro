package com.xiaodou.wallet.agent.util;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;

/**
 * @name @see com.xiaodou.wallet.util.WalletConfig.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 钱包配置文件
 * @version 1.0
 */
public class WalletConfig {
  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil
      .getInstance("/conf/custom/env/wallet_config.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null)
      synchronized (ConfigProp.class) {
        if (config == null)
          config = FileUtil.getInstance("/conf/custom/env/wallet_config.properties");
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

  public static String getKey(String productLine) {
    return getParams(String.format("wallet.key_%s", productLine));
  }

  public static String createAccountUrl() {
    return getParams("wallet.createAccountUrl");
  }

  public static String goodsListUrl() {
    return getParams("wallet.goodsListUrl");
  }

  public static String payAmountUrl() {
    return getParams("wallet.payAmountUrl");
  }

  public static String orderUrl() {
    return getParams("wallet.orderUrl");
  }

  public static String rechargeAmountUrl() {
    return getParams("wallet.rechargeAmountUrl");
  }

  public static String queryAmountWalletUrl() {
    return getParams("wallet.queryAmountWalletUrl");
  }

  public static String queryAccountWalletBill() {
    return getParams("wallet.queryAccountWalletBillUrl");
  }

}
