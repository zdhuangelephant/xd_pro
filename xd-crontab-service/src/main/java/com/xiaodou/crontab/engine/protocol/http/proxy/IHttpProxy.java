package com.xiaodou.crontab.engine.protocol.http.proxy;

import com.xiaodou.common.http.model.HttpResult;

public interface IHttpProxy {

  /**
   * GetHttpResult方法
   */
  public HttpResult getHttpResult() throws Exception;

}
