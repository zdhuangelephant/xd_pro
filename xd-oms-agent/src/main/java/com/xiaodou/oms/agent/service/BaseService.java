package com.xiaodou.oms.agent.service;

import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.oms.agent.util.MD5Util;
import com.xiaodou.oms.agent.util.OrderConfigService;

/**
 * Created by zyp on 14-7-1.
 */
public class BaseService {

  private HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();

  /**
   * http 请求
   * 
   * @param path 路径
   * @param body json串
   * @return
   */
  protected HttpResult httpRequest(String path, String body, String timeOut, String retry,
      Boolean ifRecordInout) {
    String host = OrderConfigService.getParams("oms.connection.host");
    Integer port = Integer.parseInt(OrderConfigService.getParams("oms.connection.port"));
    String protocol = OrderConfigService.getParams("oms.connection.protocol");
    String url = protocol + "://" + host + ":" + port.toString() + path;
    HttpUtil http = null;
    if (StringUtils.isBlank(timeOut) || StringUtils.isBlank(retry)) {
      http = HttpUtil.getInstance(host, port, "http");
    } else {
      http =
          HttpUtil.getInstance(host, port, "http",
              Integer.parseInt(timeOut), Integer.parseInt(retry));
    }

    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    HttpResult result = null;
    try {
      body = MD5Util.getMD5ValiJson(body);
      http.setMethod(HttpMethodUtil.getPostMethod(url, "application/json", "utf-8", body));
      result = http.getHttpResult();
    } catch (Exception e) {
      // 异常日志
      LoggerUtil.error("不支持编码格式", e);
    }
    InOutEntity msg = new InOutEntity();
    msg.setTargetUrl(url);
    if (ifRecordInout) {
      // inOut日志
      msg.setParams(body);
      msg.setResult(result);
    } else {
      msg.setUseTime(result.getHttpTime());
    }
    LoggerUtil.inOutInfo(msg);
    return result;
  }

  /**
   * 请求错误
   * 
   * @param url
   * @param body
   * @param obj
   * @param <T>
   * @return
   */
  public <T> T sendHttp(String url, String body, Class<T> obj, String timeOut, String retry,
      Boolean ifRecordInout) {
    try {
      HttpResult result = this.httpRequest(url, body, timeOut, retry, ifRecordInout);
      if (result.isResultOk()) {
        return JSON.parseObject(result.getContent(), obj);
      } else {
        /** 异常日志 */
        LoggerUtil.error("OMS调用异常.retCode=" + result.getStatusCode(), null == result.getErr()
            ? new RuntimeException()
            : result.getErr());
        /** TODO alarm 日志 */
        // AlarmEntity entity = new AlarmEntity(AlarmEntityType.HTTP_SERVICE_UNVALID.getType());
        // entity.setUrl(CommUtil.getServerName(), url, Integer.toString(result.getStatusCode()),
        // HttpStatusSwitch.getStatus(result.getStatusCode()), null == result.getErr()
        // ? new RuntimeException().getMessage()
        // : result.getErr().getMessage());
        // LoggerUtil.alarmInfo(entity);
      }
    } catch (Exception e) {
      LoggerUtil.error("未知异常", e);
    }
    return null;
  }
}
