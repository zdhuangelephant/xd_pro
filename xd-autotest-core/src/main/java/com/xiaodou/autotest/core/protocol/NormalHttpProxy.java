package com.xiaodou.autotest.core.protocol;

import java.net.MalformedURLException;
import java.util.LinkedList;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;

import com.google.common.collect.Lists;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.NormalHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP请求代理类-封装了日志操作
 * @version 1.0
 */
public class NormalHttpProxy extends AbstractHttpProxy {

  /** jsonStr json参数 */
  private LinkedList<NameValuePair> paramList = Lists.newLinkedList();

  public NormalHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public NormalHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public NormalHttpProxy(String url) throws MalformedURLException {
    this(url, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  public NormalHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    super(host, port, baseurl, timeOut, retry);
  }

  public NormalHttpProxy(String host, Integer port, String baseurl) {
    this(host, port, baseurl, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  @Override
  public HttpMethod initMethod(String baseUrl) throws Exception {
    getRealRequest().getHeader().put("Content-Type", "application/x-www-form-urlencoded");
    getRealRequest().setFormat(DataFormat.NameValuePair);
    getRealRequest().setBody(this.paramList);
    NameValuePair[] params = new NameValuePair[this.paramList.size()];
    return HttpMethodUtil.getPostMethod(baseUrl, this.paramList.toArray(params));
  }

  @Override
  public String getParams() {
    return FastJsonUtil.toJson(paramList);
  }

  @Override
  public void setParam(String name, String value) {
    addParam(name, value);
    paramList.add(new NameValuePair(name, value));
  }

}
