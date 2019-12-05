package com.xiaodou.crontab.engine.protocol.http.proxy;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;
import com.xiaodou.crontab.engine.protocol.http.proxy.param.JsonHttpParam;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.JsonHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP-JSON请求代理类-封装了日志操作
 * @version 1.0
 */
public class JsonHttpProxy extends AbstractHttpProxy<JsonHttpParam> {

  public JsonHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public JsonHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl, String queryurl, Integer timeOut,
      Integer retry) {
    super(host, port, baseurl, queryurl, timeOut, retry);
  }

  @Override
  public HttpMethod initMethod() throws Exception {
    return HttpMethodUtil.getPostMethod(getFullPath(), "application/x-www-form-urlencoded",
        "utf-8", getParam().getJson());
  }

  @Override
  public String getParams() {
    return getParam().getJson();
  }

  @Override
  public void initParam(CrontHttpProtocolConfig config) {
    setParam(new JsonHttpParam(config));
  }
}
