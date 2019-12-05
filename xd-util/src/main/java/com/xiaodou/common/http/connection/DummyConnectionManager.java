package com.xiaodou.common.http.connection;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * A connection manager that creates a single connection. Meant to be used only once.
 */
public class DummyConnectionManager implements HttpConnectionManager {

  private HttpConnection httpConnection;

  private HttpConnectionManagerParams params = new HttpConnectionManagerParams();

  public void closeIdleConnections(long idleTimeout) {}

  public HttpConnection getConnection() {
    return httpConnection;
  }

  public HttpConnection getConnectionWithTimeout(HostConfiguration hostConfiguration, long timeout) {

    httpConnection = new HttpConnection(hostConfiguration);
    httpConnection.setHttpConnectionManager(this);
    httpConnection.getParams().setDefaults(params);
    return httpConnection;
  }

  /**
   * @deprecated
   */
  public HttpConnection getConnection(HostConfiguration hostConfiguration, long timeout)
      throws HttpException {
    return getConnectionWithTimeout(hostConfiguration, timeout);
  }

  public HttpConnection getConnection(HostConfiguration hostConfiguration) {
    return getConnectionWithTimeout(hostConfiguration, -1);
  }

  public void releaseConnection(HttpConnection conn) {
    conn.close();
  }

  public HttpConnectionManagerParams getParams() {
    return params;
  }

  public void setParams(HttpConnectionManagerParams params) {
    this.params = params;
  }
}
