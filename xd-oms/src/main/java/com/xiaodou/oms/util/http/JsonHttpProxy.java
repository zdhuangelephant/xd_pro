package com.xiaodou.oms.util.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.oms.util.OrderLoggerUtil;

/**
 * <p>
 * HTTP-JSON请求代理类-封装了日志操作
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月8日
 */
public class JsonHttpProxy {

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

  private String jsonStr;

  private Integer timeOut = 5000;

  private Integer retry = 1;

  private Integer getDefaultPort() {
    Integer port = portMap.get(protocol);
    return null != port && -1 != port ? port : 80;
  }

  public JsonHttpProxy(String url, String jsonStr, Integer timeOut, Integer retry)
      throws MalformedURLException {
    if (!url.startsWith(protocol)) url = protocol + url;
    URL urls = new URL(url);
    this.port = -1 == urls.getPort() ? getDefaultPort() : urls.getPort();
    this.host = urls.getHost();
    this.baseurl = urls.getPath();
    this.jsonStr = jsonStr;
    this.timeOut = timeOut;
    this.retry = retry;
  }

  public JsonHttpProxy(String protocol, String url, String jsonStr, Integer timeOut, Integer retry)
      throws MalformedURLException {
    if (StringUtils.isNotBlank(protocol)) this.protocol = protocol;
    if (!url.startsWith(this.protocol)) url = this.protocol + this.delegx + url;
    URL urls = new URL(url);
    this.port = -1 == urls.getPort() ? getDefaultPort() : urls.getPort();
    this.host = urls.getHost();
    this.baseurl = urls.getPath();
    this.jsonStr = jsonStr;
    this.timeOut = timeOut;
    this.retry = retry;
  }

  public JsonHttpProxy(String url, String jsonStr) throws MalformedURLException {
    this(url, jsonStr, 5000, 1);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl, String jsonStr, Integer timeOut,
      Integer retry) {
    this.host = host;
    this.port = port;
    this.baseurl = baseurl;
    this.jsonStr = jsonStr;
    this.timeOut = timeOut;
    this.retry = retry;
  }

  public JsonHttpProxy(String host, Integer port, String baseurl, String jsonStr) {
    this(host, port, baseurl, jsonStr, 5000, 1);
  }

  /**
   * 测试GetHttpResult方法
   * 
   * @throws UnsupportedEncodingException
   */
  public HttpResult getHttpResult() throws UnsupportedEncodingException {
    HttpUtil http = HttpUtil.getInstance(host, port, "http", timeOut, retry);
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    http.setMethod(HttpMethodUtil.getPostMethod(baseurl, "application/json", "utf-8", jsonStr));
    HttpResult result = http.getHttpResult();
    InOutEntity msg = new InOutEntity();
    String targetUrl = host + ":" + port + baseurl;
    msg.setTargetUrl(targetUrl);
    msg.setParams(jsonStr);
    msg.setResult(result);
    OrderLoggerUtil.inOutInfo(msg);
    if (!result.isResultOk()) { // 发送报警日志
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.HTTP_SERVICE_UNVALID.getType());
      // entity.setUrl(CommUtil.getServerName(), targetUrl,
      // Integer.toString(result.getStatusCode()),
      // result.getStatusDesc(), null == result.getErr() ? null : result.getErr().getMessage());
      // OrderLoggerUtil.alarmInfo(entity);
    }
    return result;
  }

}
