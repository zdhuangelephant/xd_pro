package com.xiaodou.oms.util.http;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/25 下午6:35
 */
public class HttpHandler {


  public static HttpResult sendGetMethod(String url, String params) {
    String fullUrl = url + "?" + params;
    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(5000);
    HttpMethod getMethod = HttpMethodUtil.getGetMethod(fullUrl);
    httpUtil.setMethod(getMethod);
    HttpResult result = httpUtil.getHttpResult();
    // inOut日志
    InOutEntity msg = new InOutEntity();
    msg.setTargetUrl(fullUrl);
    msg.setParams(params);
    msg.setResult(result);
    LoggerUtil.inOutInfo(msg);
    if (!result.isResultOk()) { // 发送报警日志
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.HTTP_SERVICE_UNVALID.getType());
      // entity.setUrl(CommUtil.getServerName(), fullUrl, Integer.toString(result.getStatusCode()),
      // result.getStatusDesc(), null == result.getErr()?null:result.getErr().getMessage());
      // OrderLoggerUtil.alarmInfo(entity);
    }
    return result;
  }

}
