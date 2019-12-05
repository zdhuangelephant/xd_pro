package com.xiaodou.sms.service.platform;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.sms.service.platform.chuangrui.ChuangruiSmsService;
import com.xiaodou.sms.service.platform.huyi.HuyiSmsService;

public class PlatformServiceFactory {

  private static Map<String, ISmsPlatformService> serviceMap =
      new HashMap<String, ISmsPlatformService>();

  static {
    if (0 == serviceMap.size()) {
      serviceMap.put("1", new HuyiSmsService());
      serviceMap.put("2", new ChuangruiSmsService());
    }
  }

  public static ISmsPlatformService getSmsPlatformService(String merchantId) {
    return serviceMap.get(merchantId);
  }

}
