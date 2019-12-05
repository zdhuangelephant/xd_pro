package com.xiaodou.oms.vo.input.sign;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;

/**
 * <p>
 * 签名验证开关
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月10日
 */
public class SignSwitcher {

  private static String filterSwitch = "FILTERSWITCH";

  static {
    init();
  }

  public static boolean validate() {
    return "ON".equals(filterSwitch);
  }

  private static void init() {
    String params = ConfigProp.getParams(filterSwitch);
    if (StringUtils.isNotBlank(params)) filterSwitch = params;
  }

}
