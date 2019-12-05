package com.xiaodou.crontab.engine.protocol.http.proxy;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;
import com.xiaodou.crontab.engine.protocol.http.proxy.param.SoapHttpParam;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.SoapHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP-SOAP请求代理类-封装了日志操作
 * @version 1.0
 */
public class SoapHttpProxy extends AbstractHttpProxy<SoapHttpParam> {

  public SoapHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String host, Integer port, String baseurl, String queryurl, Integer timeOut,
      Integer retry) {
    super(host, port, baseurl, queryurl, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  public SoapHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
    // TODO Auto-generated constructor stub
  }

  @Override
  public HttpMethod initMethod() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void initParam(CrontHttpProtocolConfig config) {
    // TODO Auto-generated method stub
  }
}
