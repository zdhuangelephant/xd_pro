package com.xiaodou.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.axis.encoding.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.util.ByteArrayBuffer;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.http.socket.XdSecureProtocolSocketFactory;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * HTTP工具类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-20
 */
public class HttpUtil {

  @SuppressWarnings("deprecation")
  private Protocol sslProtocol = new Protocol("https", new XdSecureProtocolSocketFactory(), 443);

  public enum ProtocolEnum {
    HTTP, HTTPS
  }

  /**
   * client端连接
   */
  private HttpClient client;

  public HttpClient getClient() {
    return client;
  }

  /**
   * 返回结果信息
   */
  private HttpResult result;

  private int retrycount = 0;

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  private HttpMethod method;

  private HttpUtil() {
    this.client = HttpClientUtil.getInstance();
    this.result = new HttpResult();
  }

  private HttpUtil(HttpConnectionManager connectionManager) {
    this.client = HttpClientUtil.getInstance(connectionManager);
    this.result = new HttpResult();
  }

  public static HttpUtil getInstance() {
    return new HttpUtil().setConnectionTimeout(10000).setDefaultMaxConnectionsPerHost(32)
        .setMaxConnection(256);
  }

  public static HttpUtil getInstance(HttpConnectionManager connectionManager) {
    return new HttpUtil(connectionManager).setConnectionTimeout(10000)
        .setDefaultMaxConnectionsPerHost(32).setMaxConnection(256);
  }

  public static HttpUtil getInstance(final String host, int port, final ProtocolEnum protocol,
      int timeout, int retryTime) {
    return getInstance().setHost(host, port, protocol.toString().toLowerCase())
        .setConnectionTimeout(timeout).setRetryTime(retryTime).setDefaultMaxConnectionsPerHost(32)
        .setMaxConnection(256);
  }

  public static HttpUtil getInstance(final String host, int port, final String protocol,
      int timeout, int retryTime) {
    return getInstance().setHost(host, port, protocol).setConnectionTimeout(timeout)
        .setRetryTime(retryTime).setDefaultMaxConnectionsPerHost(32).setMaxConnection(256);
  }

  public static HttpUtil getInstance(final String host, int port, final String protocol,
      int timeout, int retryTime, int defaultMaxConnectionsPerHost, int maxConnection) {
    return getInstance().setHost(host, port, protocol).setConnectionTimeout(timeout)
        .setRetryTime(retryTime).setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost)
        .setMaxConnection(maxConnection);
  }

  public static HttpUtil getInstance(final String host, int port, final String protocol) {
    return getInstance().setHost(host, port, protocol).setConnectionTimeout(10000)
        .setDefaultMaxConnectionsPerHost(32).setMaxConnection(256);
  }

  public static HttpUtil getInstance(HttpConnectionManager connectionManager, final String host,
      int port, final String protocol, int timeout, int retryTime) {
    return getInstance(connectionManager).setHost(host, port, protocol)
        .setConnectionTimeout(timeout).setRetryTime(retryTime).setDefaultMaxConnectionsPerHost(32)
        .setMaxConnection(256);
  }

  public static HttpUtil getInstance(HttpConnectionManager connectionManager, final String host,
      int port, final String protocol, int timeout, int retryTime,
      int defaultMaxConnectionsPerHost, int maxConnection) {
    return getInstance(connectionManager).setHost(host, port, protocol)
        .setConnectionTimeout(timeout).setRetryTime(retryTime)
        .setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost)
        .setMaxConnection(maxConnection);
  }

  public static HttpUtil getInstance(HttpConnectionManager connectionManager, final String host,
      int port, final String protocol) {
    return getInstance(connectionManager).setHost(host, port, protocol).setConnectionTimeout(10000)
        .setDefaultMaxConnectionsPerHost(32).setMaxConnection(256);
  }

  public HttpUtil setHost(final String host, int port, final String protocolName) {

    Protocol protocol = null;
    if (protocolName.equals("https")) {
      protocol = sslProtocol;
    } else {
      protocol = Protocol.getProtocol(protocolName);
    }
    this.client.getHostConfiguration().setHost(host, port, protocol);
    return this;
  }

  public HttpUtil setMaxConnection(final Integer maxConnection) {
    this.client.getHttpConnectionManager().getParams().setMaxTotalConnections(maxConnection);
    return this;
  }

  public HttpUtil setDefaultMaxConnectionsPerHost(final Integer maxConnection) {
    this.client.getHttpConnectionManager().getParams()
        .setDefaultMaxConnectionsPerHost(maxConnection);
    return this;
  }

  public HttpUtil setConnectionTimeout(int time) {
    this.client.getHttpConnectionManager().getParams().setConnectionTimeout(time);
    this.client.getHttpConnectionManager().getParams().setSoTimeout(time);
    return this;
  }

  public HttpUtil setRetryTime(int retryCount) {
    this.retrycount = retryCount;
    return this;
  }

  public HttpUtil setParameter(String name, Object value) {
    this.client.getParams().setParameter(name, value);
    return this;
  }

  /**
   * 获取请求数据
   * 
   * @param codeType
   * @return
   */
  public HttpResult getHttpResult() {
    result.setStartTime(System.currentTimeMillis());
    try {
      result.setStatusCode(client.executeMethod(method));
      StatusLine status = method.getStatusLine();
      result.setHttpStatue(status.getStatusCode());
      if (null != status) {
        if (getOkStatus(result.getHttpStatue())) {
          result.setStatusCode(HttpResultStatus.OK);
          setResponseInfo();
        } else if (isRedirectStatus(result.getHttpStatue())) {
          redirect();
        } else {
          result.setContent(null);
        }
      }
    } catch (HttpException e) {
      setStatusCode(HttpResultStatus.STATUS_CODE_CONNERR, e);
    } catch (IOException e) {
      setStatusCode(HttpResultStatus.STATUS_CODE_TIMEOUT, e);
      retryConn();
    } catch (Exception e) {
      setStatusCode(HttpResultStatus.STATUS_CODE_OTHER, e);
      retryConn();
    } finally {
      if (null != method && method.hasBeenUsed()) {
        method.releaseConnection();
      }
      client = null;
      method = null;
      result.setEndTime(System.currentTimeMillis());
    }
    return result;
  }

  private void setResponseInfo() throws IOException {
    if (null != method) {
      result.setHeaders(method.getResponseHeaders());
      result.setFooters(method.getResponseFooters());
    }
    setResultContent();
    if (null != client && null != client.getState())
      result.setCookies(client.getState().getCookies());
  }

  private void setResultContent() throws IOException {
    if (null != method) {
      for (Header header : result.getHeaders()) {
        if (header.getName().equals("Content-Encoding")
            && StringUtils.isNotBlank(header.getValue()) && header.getValue().contains("gzip")) {
          StringBuffer _result = new StringBuffer(200);
          try (GZIPInputStream gis = new GZIPInputStream(method.getResponseBodyAsStream())) {
            readContent(gis, _result);
          }
          result.setContent(_result.toString());
          return;
        } else if (header.getName().equals("Content-Type")
            && StringUtils.isNotBlank(header.getValue()) && header.getValue().contains("image")) {
          result.setContentType(HttpResultContentType.IMAGE);
          StringBuffer _result = new StringBuffer(200);
          readPicContent(method.getResponseBodyAsStream(), _result);
          result.setContent(_result.toString());
          return;
        }
      }
      result.setContentType(HttpResultContentType.TEXT);
      result.setContent(method.getResponseBodyAsString());
    }
  }

  private void readContent(InputStream in, StringBuffer result) throws IOException {
    int count, total = 0;
    byte data[] = new byte[1024 * 4];
    ByteArrayBuffer buffer = new ByteArrayBuffer(1024 * 4);
    while ((count = in.read(data, 0, 1024 * 4)) != -1) {
      buffer.append(data, 0, count);
      total += count;
    }
    result.append(new String(buffer.buffer(), 0, total, "UTF-8"));
  }

  private void readPicContent(InputStream in, StringBuffer result) throws IOException {
    int count, total = 0;
    byte data[] = new byte[1024 * 4];
    ByteArrayBuffer buffer = new ByteArrayBuffer(1024 * 4);
    while ((count = in.read(data, 0, 1024 * 4)) != -1) {
      buffer.append(data, 0, count);
      total += count;
    }
    result.append(Base64.encode(buffer.buffer(), 0, total));
  }

  public void excute() {
    try {
      client.executeMethod(method);
    } catch (IOException e) {
      LoggerUtil.error("IoException", e);
    }
  }

  /**
   * 设置状态码
   * 
   * @param status
   * @param e
   */
  private void setStatusCode(HttpResultStatus status, Exception e) {
    if (retrycount == 0) {
      if (getOkStatus(result.getHttpStatue())) {
        result.setStatusCode(HttpResultStatus.OK);
      } else {
        result.setStatusCode(status);
        result.setErr(e);
      }
    }
  }

  /**
   * 重试连接
   */
  private void retryConn() {
    while ((this.retrycount--) > 0) {
      getHttpResult();
    }
  }

  private boolean getOkStatus(Integer statueCode) {
    return null != statueCode && statueCode >= 200 && statueCode < 300;
  }

  private boolean isRedirectStatus(Integer statusCode) {
    return statusCode == 301 || statusCode == 302;
  }

  public HttpResult redirect() throws IOException {
    boolean isRedirect = false;
    for (Header header : method.getResponseHeaders()) {
      if (header.getName().equals("Location")) {
        String location = header.getValue();
        if (null != method && method.hasBeenUsed()) {
          method.releaseConnection();
        }
        method = HttpMethodUtil.getGetMethod(location);
        isRedirect = true;
      }
      if (header.getName().equals("Set-Cookie")) {
        String cookie = header.getValue();
        method.addRequestHeader("Cookie", cookie);
      }
    }
    if (isRedirect) {
      return getHttpResult();
    }
    result.setStatusCode(HttpResultStatus.REDIRECT);
    setResponseInfo();
    return result;
  }
}
