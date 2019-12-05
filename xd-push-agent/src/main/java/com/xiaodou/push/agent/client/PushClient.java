package com.xiaodou.push.agent.client;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.validation.Errors;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.HttpUtil.ProtocolEnum;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.push.agent.prop.ServerConfigProp;

/**
 * @name @see cpm.xiaodou.push.agent.client.PushMessageClient.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 推送客户端
 * @version 1.0
 */
public class PushClient extends BaseController {

  /**
   * 验证消息体
   * 
   * @param message 消息体
   */
  private static void validate(BaseValidatorPojo message) {
    if (null == message) throw new RuntimeException("推送消息为空");
    Errors errors = message.validate();
    if (errors.hasErrors()) throw new RuntimeException("消息体校验失败:" + getErrMsg(errors));
  }

  /**
   * 业务系统调用推送通知消息方法
   * 
   * @param message 消息体
   */
  public static HttpResult push(Message message) {
    try {
      validate(message);
      return sendToMS(message);
    } catch (Exception e) {
      LoggerUtil.error("推送消息至消息服务失败", e);
    }
    return null;
  }
  
  /**
   * 业务后台系统手动调用推送通知消息方法
   * 
   * @param message 消息体
   */
  public static HttpResult handPush(Message message) {
    try {
      validate(message);
      return sendToMSFromHand(message);
    } catch (Exception e) {
      LoggerUtil.error("推送消息至消息服务失败", e);
    }
    return null;
  }

  /**
   * 业务系统调用推送消息短信方法
   * 
   * @param message 消息体
   */
  public static HttpResult push(ShortMessage shortMessage) {
    try {
      validate(shortMessage);
      return sendToMS(shortMessage);
    } catch (Exception e) {
      LoggerUtil.error("推送消息至消息服务失败", e);
    }
    return null;
  }

  /**
   * 将消息发送至消息服务器方法
   * 
   * @param message 消息体
   * @throws UnsupportedEncodingException
   */
  private static HttpResult sendToMS(Message message) throws UnsupportedEncodingException {
    String host = ServerConfigProp.getParams(ServerConfigProp.HOST);
    int port = ServerConfigProp.getInt(ServerConfigProp.PORT);
    String url = ServerConfigProp.getParams(ServerConfigProp.PUSH_MESSAGE_URL);
    int timeout = ServerConfigProp.getInt(ServerConfigProp.PUSH_MESSAGE_TIMEOUT);
    int retrytime = ServerConfigProp.getInt(ServerConfigProp.PUSH_MESSAGE_RETRY);
    return sendHttpWithLog(host, port, url, message.getParams(), timeout, retrytime);
  }
  
  /**
   * 将消息发送至消息服务器方法
   * 
   * @param message 消息体
   * @throws UnsupportedEncodingException
   */
  private static HttpResult sendToMSFromHand(Message message) throws UnsupportedEncodingException {
    String host = ServerConfigProp.getParams(ServerConfigProp.HOST);
    int port = ServerConfigProp.getInt(ServerConfigProp.PORT);
    String url = ServerConfigProp.getParams(ServerConfigProp.HAND_PUSH_MESSAGE_URL);
    int timeout = ServerConfigProp.getInt(ServerConfigProp.PUSH_MESSAGE_TIMEOUT);
    int retrytime = ServerConfigProp.getInt(ServerConfigProp.PUSH_MESSAGE_RETRY);
    return sendHttpWithLog(host, port, url, message.getParams(), timeout, retrytime);
  }

  /**
   * 将短信发送至消息服务器方法
   * 
   * @param message 消息体
   * @throws UnsupportedEncodingException
   */
  private static HttpResult sendToMS(ShortMessage shortMessage) throws UnsupportedEncodingException {
    String host = ServerConfigProp.getParams(ServerConfigProp.HOST);
    int port = ServerConfigProp.getInt(ServerConfigProp.PORT);
    String url = ServerConfigProp.getParams(ServerConfigProp.PUSH_SHORT_MESSAGE_URL);
    int timeout = ServerConfigProp.getInt(ServerConfigProp.PUSH_SHORT_MESSAGE_TIMEOUT);
    int retrytime = ServerConfigProp.getInt(ServerConfigProp.PUSH_SHORT_MESSAGE_RETRY);
    return sendHttpWithLog(host, port, url, shortMessage.getParams(), timeout, retrytime);
  }

  /**
   * 发送消息同时记录日志信息
   * 
   * @param host
   * @param port
   * @param url
   * @param params
   * @param timeout
   * @param retrytime
   * @return
   * @throws UnsupportedEncodingException
   */
  private static HttpResult sendHttpWithLog(String host, int port, String url, String params,
      int timeout, int retrytime) throws UnsupportedEncodingException {
    HttpUtil http = HttpUtil.getInstance(host, port, ProtocolEnum.HTTP, timeout, retrytime);
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    NameValuePair pair = new NameValuePair();
    pair.setName("req");
    pair.setValue(params);
    HttpMethod httpMethod = HttpMethodUtil.getPostMethod(url, new NameValuePair[] {pair});
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl(String.format("%s://%s:%s/%s", "http", host, port, url));
    entity.setParams(params);
    entity.setResult(httpResult);
    LoggerUtil.inOutInfo(entity);
    return httpResult;
  }

}
