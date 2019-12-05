package com.xiaodou.server.mapi.engine.proxy;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.engine.http.NormalHttpProxy;
import com.xiaodou.server.mapi.engine.model.Api;
import com.xiaodou.server.mapi.engine.model.Param;

public class HttpApiProxy extends AbstractApiProxy {

  public HttpApiProxy() {}

  @Override
  public HttpResult excute(Object... paramPojo) throws Exception {
    Api api = getApi();
    // JsonHttpProxy proxy = (JsonHttpProxy) Format.json.getProxyType()
    // .getConstructor(String.class).newInstance(api.getUrl());
    NormalHttpProxy proxy =
        (NormalHttpProxy) api.getFormat().getProxyType().getConstructor(String.class)
            .newInstance(api.getUrl());
    if (null != api.getProtocol()) proxy.setProtocol(api.getProtocol());
    Map<String, String> headers = Maps.newHashMap();
    if (null != paramPojo) {
      if (paramPojo.length > 0) {
        Object param = paramPojo[0];
        List<Param> paramList = api.getParams();
        proxy.setParams(param, paramList);
      }
      if (paramPojo.length > 1) {
        Object head = paramPojo[1];
        headers =
            FastJsonUtil.fromJsons(FastJsonUtil.toJson(head),
                new TypeReference<Map<String, String>>() {});
      }
    }
    return proxy.getHttpResult(headers);
  }
}
