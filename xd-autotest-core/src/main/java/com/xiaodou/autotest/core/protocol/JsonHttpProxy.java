package com.xiaodou.autotest.core.protocol;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.common.http.HttpMethodUtil;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.JsonHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP-JSON请求代理类-封装了日志操作
 * @version 1.0
 */
public class JsonHttpProxy extends AbstractHttpProxy {

  /** jsonStr json参数 */
  private String jsonStr;

  public String getJsonStr() {
    return jsonStr;
  }

  public void setJsonStr(String jsonStr) {
    this.jsonStr = jsonStr;
  }

  public JsonHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public JsonHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public JsonHttpProxy(String url) throws MalformedURLException {
    this(url, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    super(host, port, baseurl, timeOut, retry);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl) {
    this(host, port, baseurl, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  @Override
  public HttpMethod initMethod(String baseUrl) throws Exception {
    getRealRequest().getHeader().put("Content-Type", "application/json");
    getRealRequest().setFormat(DataFormat.jSon);
    getRealRequest().setBody(jsonStr);
    return HttpMethodUtil.getPostMethod(baseUrl, "application/json", "utf-8", jsonStr);
  }

  @Override
  public String getParams() {
    return jsonStr;
  }

  @Override
  public void setParam(String name, String value) {
    this.jsonStr = value;
  }
}
