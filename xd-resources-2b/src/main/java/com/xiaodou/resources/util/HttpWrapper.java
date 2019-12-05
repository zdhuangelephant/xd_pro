package com.xiaodou.resources.util;

import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;

public class HttpWrapper {

  public static String send(String host, int port, String path, int retryTime, int timeout,
      String content, Map<String, String> headers) throws Exception {

    HttpUtil http = HttpUtil.getInstance(host, port, "http", timeout, retryTime);
    HttpMethod httpMethod =
        HttpMethodUtil.getPostMethod(path, "application/x-www-form-urlencoded", "utf-8", content);
    for (String headerName : headers.keySet()) {
      httpMethod.setRequestHeader(headerName, headers.get(headerName));
    }
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
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
