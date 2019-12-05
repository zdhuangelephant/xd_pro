package com.xiaodou.crontab.engine.enums;

import com.xiaodou.crontab.engine.protocol.http.proxy.AbstractHttpProxy;
import com.xiaodou.crontab.engine.protocol.http.proxy.JsonHttpProxy;
import com.xiaodou.crontab.engine.protocol.http.proxy.NoneParamHttpProxy;
import com.xiaodou.crontab.engine.protocol.http.proxy.NormalHttpProxy;
import com.xiaodou.crontab.engine.protocol.http.proxy.SoapHttpProxy;

/**
 * @name @see com.xiaodou.crontab.engine.enums.DataProtocol.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 参数传输格式
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public enum DataFormat {

  /** none 无参 Get请求 */
  none(NoneParamHttpProxy.class),
  /** normal NameValuePair Post请求 */
  normal(NormalHttpProxy.class),
  /** json Json Post请求 */
  json(JsonHttpProxy.class),
  /** soap SoapXml Post请求 */
  soap(SoapHttpProxy.class);
  DataFormat(Class<? extends AbstractHttpProxy> proxyType) {
    this.proxyType = proxyType;
  }

  private Class<? extends AbstractHttpProxy> proxyType;

  public Class<? extends AbstractHttpProxy> getProxyType() {
    return proxyType;
  }
}
