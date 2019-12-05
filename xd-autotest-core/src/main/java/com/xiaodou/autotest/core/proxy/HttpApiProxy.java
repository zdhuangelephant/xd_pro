package com.xiaodou.autotest.core.proxy;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.autotest.core.protocol.AbstractHttpProxy;
import com.xiaodou.autotest.core.vo.ApiRequest;
import com.xiaodou.autotest.core.vo.ApiResult;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.proxy.HttpApiProxy.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月11日
 * @description HttpApi执行代理
 * @version 1.0
 */
public class HttpApiProxy extends AbstractApiProxy {

  public HttpApiProxy() {}

  @Override
  public ApiResult excute0() {
    Api api = getApi();
    ApiResult result = new ApiResult();
    result.setFormat(api.getResultDataFormat());
    try {
      AbstractHttpProxy proxy =
          (AbstractHttpProxy) api.getFormat().getProxyType().getConstructor(String.class)
              .newInstance(api.getUrl());
      if (null != api.getProtocol()) {
        proxy.setProtocol(api.getProtocol());
      }
      Map<String, String> headers = Maps.newHashMap();
      List<Param> params = getApi().getParams();
      if (null != params && params.size() > 0) {
        for (Param param : params) {
          if (StringUtils.isBlank(param.getName()) || StringUtils.isBlank(param.getParamValue())) {
            continue;
          }
          if (ParamType.HeaderParam.equals(param.getParamType())) {
            headers.put(param.getName(), param.getParamValue());
          }
          if (ParamType.QueryParam.equals(param.getParamType())) {
            proxy.setParam(param.getName(), param.getParamValue());
          }
        }
      }
      HttpResult httpResult = proxy.getHttpResult(headers);
      ApiRequest request = proxy.getRealRequest();
      api.setApiRequest(request);
      initApiResult(result, httpResult);
    } catch (Exception e) {
      result.setHasError(Boolean.TRUE);
      result.setErrorMessage(StringUtils.toString(e));
    }
    return result;
  }

  private void initApiResult(ApiResult result, HttpResult httpResult) {
    if (null != httpResult.getHeaders()) {
      for (Header header : httpResult.getHeaders()) {
        result.getHeader().put(header.getName(), header.getValue());
      }
    }
    result.setHttpStatus(httpResult.getHttpStatue());
    result.setStartTime(new Timestamp(httpResult.getStartTime()));
    result.setEndTime(new Timestamp(httpResult.getEndTime()));
    result.setCostTime(httpResult.getHttpTime());
    if (StringUtils.isNotBlank(httpResult.getContent())) {
      result.setSrcResult(httpResult.getContent());
    } else {
      result.setSrcResult(httpResult.getStatusDesc());
    }
    if (null != httpResult.getErr()) {
      result.setHasError(Boolean.TRUE);
      result.setErrorMessage(StringUtils.toString(httpResult.getErr()));
    }
  }

}
