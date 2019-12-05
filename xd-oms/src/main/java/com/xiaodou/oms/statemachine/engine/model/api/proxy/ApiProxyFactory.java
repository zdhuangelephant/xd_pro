package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;



public class ApiProxyFactory {
  
  public static IApiProxy getApiProxy(IApiProxy endpoint) {
    if (endpoint instanceof LocalAPI) {
      return new LocalApiBase((LocalAPI) endpoint);
    } else if (endpoint instanceof RemoteAPI && ((RemoteAPI) endpoint).getProtocol().equals("json")) {
      return new JsonHttpApiBase((RemoteAPI) endpoint);
    } else {
      return null;
    }
  }
  
}
