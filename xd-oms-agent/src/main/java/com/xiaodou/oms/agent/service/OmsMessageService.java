package com.xiaodou.oms.agent.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

import org.apache.commons.httpclient.methods.PostMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.agent.util.OrderConfigService;
import com.xiaodou.oms.agent.vo.request.SendQQMsgRequest;
import com.xiaodou.oms.agent.vo.response.BaseResponse;

/**
 * Date: 2014/12/30
 * Time: 16:01
 *
 * @author Tian.Dong
 */
public class OmsMessageService {
  public static BaseResponse sendGroupMessage(SendQQMsgRequest request) {
    request.setType("group");
    return sendMessage(request);
  }
  public static BaseResponse sendDiscusMessage(SendQQMsgRequest request) {
    request.setType("discus");
    return sendMessage(request);
  }
  public static BaseResponse sendMessage(SendQQMsgRequest request) {
    //oms.message.send
    String host = OrderConfigService.getParams("oms.connection.host");
    Integer port = Integer.parseInt(OrderConfigService.getParams("oms.connection.port"));
    String protocol = OrderConfigService.getParams("oms.connection.protocol");
    String path = OrderConfigService.getParams("oms.message.send");

    String url = protocol + "://" + host + ":" + port.toString() + path;
    String contentType = "application/x-www-form-urlencoded";
    String charset = "utf-8";
    String params = transform(request);
    PostMethod method = null;
    try {
      method = HttpMethodUtil.getPostMethod(url, contentType, charset, params);
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.error("OmsMessageService.sendMessage异常", e);
    }

    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setMethod(method);
    HttpResult result = httpUtil.getHttpResult();
    if(result.getStatusCode() != 200) {
      BaseResponse response = new BaseResponse();
      response.setRetCode(-1);
      response.setRetDesc("httpStatus非200");
      return response;
    }
    BaseResponse response = FastJsonUtil.fromJson(result.getContent(),BaseResponse.class);
    return response;
  }

  private static String transform(Object obj) {
    StringBuilder formString = new StringBuilder();
    for (Field field : obj.getClass().getDeclaredFields()) {
      String key = field.getName();
      field.setAccessible(true);
      String value = null;
      try {
        value = (String) field.get(obj);
        value = URLEncoder.encode(value,"utf-8");
      } catch (Exception e) {
        LoggerUtil.error("OmsMessageService.transform异常", e);
      }
      formString.append(key).append("=");
      formString.append(value).append("&");
    }
    return formString.toString();
  }
}
