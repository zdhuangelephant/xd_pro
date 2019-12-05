package com.xiaodou.crontab.engine.protocol.http.proxy;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;
import com.xiaodou.crontab.engine.protocol.http.proxy.param.NormalHttpParam;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.NormalHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP请求代理类-封装了日志操作
 * @version 1.0
 */
public class NormalHttpProxy extends AbstractHttpProxy<NormalHttpParam> {

  public NormalHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public NormalHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public NormalHttpProxy(String host, Integer port, String baseurl, String queryurl,
      Integer timeOut, Integer retry) {
    super(host, port, baseurl, queryurl, timeOut, retry);
  }

  @Override
  public HttpMethod initMethod() throws Exception {
    if (null != getParam() && null != getParam().getParamList()
        && !getParam().getParamList().isEmpty()) {
      NameValuePair[] params = new NameValuePair[getParam().getParamList().size()];
      return HttpMethodUtil.getPostMethod(getFullPath(), getParam().getParamList().toArray(params));
    } else {
      return HttpMethodUtil.getGetMethod(getFullPath());
    }
  }

  @Override
  public String getParams() {
    return FastJsonUtil.toJson(getParam().getParamList());
  }

  @Override
  public void initParam(CrontHttpProtocolConfig config) {
    setParam(new NormalHttpParam(config));
  }
}
