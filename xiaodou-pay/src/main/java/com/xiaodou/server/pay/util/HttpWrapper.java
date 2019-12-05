package com.xiaodou.server.pay.util;

import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;

public class HttpWrapper {
  public static String send(String protocol, String host, int port, String path, int retryTime,
      int timeout, String content, Map<String, String> headers) throws Exception {
    HttpUtil http = HttpUtil.getInstance(host, port, protocol, timeout, retryTime);
    HttpMethod httpMethod =
        HttpMethodUtil.getPostMethod(path, "application/json", "utf-8", content);
    if (null != headers) for (String headerName : headers.keySet()) {
      httpMethod.setRequestHeader(headerName, headers.get(headerName));
    }
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
    // inOut日志
    InOutEntity msg = new InOutEntity();
    msg.setTargetUrl(protocol+host+":"+port+path);
    msg.setParams(content);
    msg.setResult(httpResult);
    LoggerUtil.inOutInfo(msg);
    if (!httpResult.isResultOk()) {
      LoggerUtil.error("[通信异常][" + httpResult.toString() + "]", httpResult.getErr() == null
          ? new RuntimeException()
          : httpResult.getErr());
      return null;
    } else {
      return httpResult.getContent();
    }
  }
}
