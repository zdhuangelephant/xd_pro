package com.xiaodou.server.mapi.engine.proxy;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.server.mapi.engine.model.Api;

/**
 * @name @see com.xiaodou.server.mapi.engine.proxy.AbstractApiProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description API组件执行代理
 * @version 1.0
 */
public abstract class AbstractApiProxy {

  /** api api组件模型 */
  private Api api;

  public Api getApi() {
    return api;
  }

  public void setApi(Api api) {
    this.api = api;
  }

  public abstract HttpResult excute(Object... paramPojo) throws Exception;

}
