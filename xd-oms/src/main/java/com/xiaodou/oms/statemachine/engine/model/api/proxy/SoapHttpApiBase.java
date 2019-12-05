package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;

/**
 * <p>
 * 基于SOAP协议的HTTPApiProxy组件
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年8月15日
 */
@SuppressWarnings("unchecked")
public class SoapHttpApiBase extends HttpApiBase {

  public SoapHttpApiBase(RemoteAPI endpoint) {
    super(endpoint);
  }

  @Override
  protected HttpResult sendRequest(Context context) throws Exception {
    // @ TODO null
    return null;
  }

}
