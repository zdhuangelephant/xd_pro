package com.xiaodou.autotest.core.protocol;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.SoapHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP-SOAP请求代理类-封装了日志操作
 * @version 1.0
 */
public class SoapHttpProxy extends AbstractHttpProxy {

  public SoapHttpProxy(String url) throws MalformedURLException {
    super(url);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String host, Integer port, String baseurl) {
    super(host, port, baseurl);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    super(host, port, baseurl, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  @Override
  public HttpMethod initMethod(String baseUrl) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setParam(String name, String value) {
    // TODO Auto-generated method stub
  }

}
