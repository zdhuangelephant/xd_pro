package com.xiaodou.crontab.engine.protocol.http.proxy;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;
import com.xiaodou.crontab.engine.protocol.http.proxy.param.HttpProxyParam;

/**
 * @name @see com.xiaodou.crontab.engine.protocol.http.proxy.NoneParamHttpProxy.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 无参数httpGet请求方法
 * @version 1.0
 */
public class NoneParamHttpProxy extends AbstractHttpProxy<HttpProxyParam> {

  public NoneParamHttpProxy(String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public NoneParamHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public NoneParamHttpProxy(String host, Integer port, String baseurl, String queryurl,
      Integer timeOut, Integer retry) {
    super(host, port, baseurl, queryurl, timeOut, retry);
  }

  @Override
  public HttpMethod initMethod() throws Exception {
    return HttpMethodUtil.getGetMethod(getFullPath());
  }

  @Override
  public String getParams() {
    return StringUtils.EMPTY;
  }

  @Override
  public void initParam(CrontHttpProtocolConfig config) {
    setParam(new HttpProxyParam(config) {});
  }
}
