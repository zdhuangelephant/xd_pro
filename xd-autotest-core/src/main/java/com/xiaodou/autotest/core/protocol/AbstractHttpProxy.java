package com.xiaodou.autotest.core.protocol;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.core.vo.ApiRequest;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.AbstractHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP请求代理类-封装了日志操作
 * @version 1.0
 */
public abstract class AbstractHttpProxy {

  private Map<String, String> paramMap = Maps.newHashMap();

  void addParam(String name, String value) {
    paramMap.put(name, value);
  }

  String getParam(String name) {
    return paramMap.get(name);
  }

  Map<String, String> getParamMap() {
    return paramMap;
  }

  private Map<String, Integer> portMap = Maps.newHashMap();

  {
    portMap.put("http", 80);
    portMap.put("https", 443);
  }

  private String protocol = "http";

  private String delegx = "://";

  private String host;

  private int port = 80;

  private String baseurl;

  private Integer timeOut = ActionConstant.DEFAULT_TIMEOUT;

  private Integer retry = ActionConstant.DEFAULT_RETRY;

  private ApiRequest realRequest = new ApiRequest();

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getBaseurl() {
    return baseurl;
  }

  public void setBaseurl(String baseurl) {
    this.baseurl = baseurl;
  }

  public Integer getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
  }

  public Integer getRetry() {
    return retry;
  }

  public void setRetry(Integer retry) {
    this.retry = retry;
  }

  private Integer getDefaultPort() {
    Integer port = portMap.get(protocol);
    return null != port && -1 != port ? port : 80;
  }

  public AbstractHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    this(null, url, timeOut, retry);
  }

  public AbstractHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    if (StringUtils.isNotBlank(protocol)) {
      this.protocol = protocol;
    }
    if (!url.startsWith(this.protocol)) {
      url = this.protocol + this.delegx + url;
    }
    URL urls = new URL(url);
    this.host = urls.getHost();
    this.port = -1 == urls.getPort() ? getDefaultPort() : urls.getPort();
    this.baseurl = urls.getPath();
    this.timeOut = timeOut;
    this.retry = retry;
    this.realRequest.setUrl(url);
  }

  public AbstractHttpProxy(String url) throws MalformedURLException {
    this(url, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  public AbstractHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    this.host = host;
    this.port = port;
    this.baseurl = baseurl;
    this.timeOut = timeOut;
    this.retry = retry;
  }

  public AbstractHttpProxy(String host, Integer port, String baseurl) {
    this(host, port, baseurl, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  /**
   * 测试GetHttpResult方法
   * 
   * @throws UnsupportedEncodingException
   */
  public HttpResult getHttpResult(Map<String, String> headers) throws Exception {
    HttpUtil http = HttpUtil.getInstance(host, port, protocol, timeOut, retry);
    HttpMethod httpMethod = initMethod(baseurl);
    if (null != headers && headers.size() > 0) {
      for (String headerName : headers.keySet()) {
        httpMethod.setRequestHeader(headerName, headers.get(headerName));
        realRequest.getHeader().put(headerName, headers.get(headerName));
      }
    }
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    http.setMethod(httpMethod);
    HttpResult result = http.getHttpResult();
    InOutEntity msg = new InOutEntity();
    String targetUrl = host + ":" + port + baseurl;
    msg.setTargetUrl(targetUrl);
    msg.setParams(getParams());
    msg.setResult(result);
    LoggerUtil.inOutInfo(msg);
    // 发送报警日志
    if (!result.isResultOk()) {
      // TODO 报警日志
    }
    return result;
  }

  /**
   * 初始化方法信息
   * 
   * @param baseUrl 基础URL
   * @return HttpMethod
   * @throws Exception 异常信息
   */
  public abstract HttpMethod initMethod(String baseUrl) throws Exception;

  /**
   * 获取参数串
   * 
   * @return 参数字符串
   */
  public abstract String getParams();

  /**
   * 设置参数
   * 
   * @param name 参数名
   * @param value 参数值
   */
  public abstract void setParam(String name, String value);

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol.toString();
  }

  public ApiRequest getRealRequest() {
    return realRequest;
  }

}
