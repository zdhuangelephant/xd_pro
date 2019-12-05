package com.xiaodou.crontab.engine.protocol.http.proxy;

import com.xiaodou.crontab.engine.enums.DataFormat;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocol;

public class HttpProxyFactory {

  public static final IHttpProxy buildProxy(CrontHttpProtocol httpProtocol) {
    if (null == httpProtocol) return null;
    DataFormat dataFormat =
        Enum.valueOf(DataFormat.class, httpProtocol.getConfig().getDataProtocol());
    if (null == dataFormat || null == dataFormat.getProxyType()) return null;
    try {
      return dataFormat
          .getProxyType()
          .getConstructor(String.class, String.class, Integer.class, Integer.class)
          .newInstance(httpProtocol.getProtocol(), httpProtocol.getTargetAddr(),
              httpProtocol.getTimeOut(), httpProtocol.getRetryTime())
          .buildParam(httpProtocol.getConfig());
    } catch (Exception e) {
      return null;
    }
  }

}
