package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.service.message.MessageContext;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.util.http.JsonHttpProxy;

/**
 * <p>
 * 基于JSON协议的HTTPApiProxy组件
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
@SuppressWarnings("unchecked")
public class JsonHttpApiBase extends HttpApiBase {

//  private static final String PROTOCOL = "application/json";
  
  public JsonHttpApiBase(RemoteAPI endpoint) {
    super(endpoint);
  }

  @Override
  protected HttpResult sendRequest(Context context) throws Exception {
//    params = ApiProxyUtil.getPostJson(context, api);
    params = FastJsonUtil.toJson(MessageContext.newInstance(context));
    JsonHttpProxy proxy = new JsonHttpProxy(api.getUrl(), params);
    HttpResult httpResult = proxy.getHttpResult();
    return httpResult;
  }

}
