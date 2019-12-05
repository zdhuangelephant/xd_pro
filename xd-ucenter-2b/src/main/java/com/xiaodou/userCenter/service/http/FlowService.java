package com.xiaodou.userCenter.service.http;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.util.GenUtil;
import com.xiaodou.userCenter.model.http.Protocol;
import com.xiaodou.userCenter.model.http.ResponseBase;



public class FlowService {

  public static <T extends ResponseBase> T doFlow(Object req, Class<T> class1,
      Protocol protocol) {
    if (protocol.getMethod().equals(Protocol.GET)) {
      return sendGetMethod(req, class1, protocol.getUrl());
    } else if (protocol.getMethod().equals(Protocol.POST)) {
      return sendPostMethod(req, class1, protocol.getUrl());
    } else {
      return null;
    }
  }

  private static <T extends ResponseBase> T dealHttpResult(String url, String params,
      HttpResult result, Class<T> responseClass) {
    // inOut日志
    InOutEntity msg = new InOutEntity();
    msg.setTargetUrl(url);
    msg.setParams(params);
    msg.setResult(result);
    LoggerUtil.inOutInfo(msg);
    // 处理http状态码200
    if (!result.isResultOk() || result.getContent() == null) {
      Exception httpException = result.getErr();
      if (httpException == null) {
        httpException = new Exception("发送http请求异常");
      }
      LoggerUtil.error("发送http请求异常,url:" + url, httpException);
      try {
        T res = responseClass.newInstance();
        res.setRetcode("20001");
        res.setRetdesc("http错误,状态码：" + result.getStatusCode());
        return res;
      } catch (Exception e) {
        e.printStackTrace();
        LoggerUtil.error("生成ResponseBase异常", e);
        return null;
      }
    }
    // 解析response
    T res = null;
    try {
      res = FastJsonUtil.fromJson(result.getContent(), responseClass);
      // res = JSON.parseObject(result.getContent(), responseClass);
      if (res == null) {
        res = responseClass.newInstance();
        res.setRetcode("20002");
        res.setRetdesc("response返回null");
        return res;
      }
    } catch (Exception e) {
      LoggerUtil.error("生成ResponseBase异常", e);
      return null;
    }
    return res;
  }

  public static <T extends ResponseBase> T sendGetMethod(Object req, Class<T> responseClass,
      String url) {
    String params = null;
    try {
      params = GenUtil.generateParams(req);
    } catch (IllegalAccessException e) {
      LoggerUtil.error("generateParams异常,url=" + url + "req=" + req.toString(), e);
    }
    String fullUrl = url + "?" + params;
    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(10000);
    httpUtil.setRetryTime(3);
    HttpMethod getMethod = HttpMethodUtil.getGetMethod(fullUrl);
    httpUtil.setMethod(getMethod);
    HttpResult result = httpUtil.getHttpResult();
    return dealHttpResult(url, params, result, responseClass);
  }

  public static <T extends ResponseBase> T sendPostMethod(Object req, Class<T> responseClass,
      String url) {
    String reqJson = FastJsonUtil.toJson(req);
    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(30000);
    httpUtil.setRetryTime(1);
    HttpMethod postMethod = null;

    try {
      postMethod = HttpMethodUtil.getPostMethod(url, "application/json", "utf-8", reqJson);
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.error("不支持编码格式", e);
    }
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    return dealHttpResult(url, reqJson, result, responseClass);
  }
}
