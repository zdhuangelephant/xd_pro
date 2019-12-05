package com.xiaodou.common.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;

import com.xiaodou.common.http.connection.DummyConnectionManager;


/**
 * HttpClient实例操作类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-20
 */
public class HttpClientUtil {

  private HttpClientUtil() {}

  private HttpClient client;

  public void setClient(HttpClient client) {
    this.client = client;
  }

  public HttpClient getClient() {
    return client;
  }

  /**
   * 用于保存client实例的本地变量
   */
  private static final ThreadLocal<HttpClientUtil> threadSession =
      new ThreadLocal<HttpClientUtil>();

  /**
   * 获取HttpClient本地实例-如果存在线程副本,返回副本
   * 
   * @return HttpClient实例
   */
  public static HttpClient getInstance() {
    return getInstance(new DummyConnectionManager());
  }
  
  public static HttpClient getInstance(HttpConnectionManager connectionManager){
    HttpClientUtil clientUtil = threadSession.get();
    if (clientUtil == null) {
      clientUtil = new HttpClientUtil();
      HttpClient client = new HttpClient();
      client.setHttpConnectionManager(connectionManager);
      clientUtil.client = client;
      threadSession.set(clientUtil);
    }
    return clientUtil.client;
  }
  
  public static HttpClient getPoolInstance(){
    HttpClientUtil clientUtil = threadSession.get();
    if (clientUtil == null) {
      clientUtil = new HttpClientUtil();
      HttpClient client = new HttpClient();
      clientUtil.client = client;
      threadSession.set(clientUtil);
    }
    return clientUtil.client;
  }

  /**
   * 获取包装器
   * 
   * @return HttpClientUtil client包装器
   */
  public static HttpClientUtil getHttpClientUtil() {
    HttpClientUtil clientUtil = (HttpClientUtil) threadSession.get();
    if (clientUtil == null) {
      clientUtil = new HttpClientUtil();
      threadSession.set(clientUtil);
    }
    return clientUtil;
  }

  /**
   * 设置包装器
   * 
   * @param clientUtil client包装器
   */
  public static void setHttpClientUtil(HttpClientUtil clientUtil) {
    threadSession.set(clientUtil);
  }
}
