package com.xiaodou.common.util.log;


import org.apache.commons.httpclient.params.HttpMethodParams;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;


/**
 * @name @see com.xiaodou.common.util.log.SendToLogServerUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月12日
 * @description
 * @version 1.0
 */
public class DashBoardAlarm {

  /**
   * 报警方法
   * 
   * @param entity
   */
  public static void alarm(AlarmEntityImpl entity) {
    String sPort = ConfigProp.getParams("ALARM_SERVICE_PORT");
    String rootUrl = ConfigProp.getParams("ALARM_SERVICE_ROOTURL");
    String sendUrl = ConfigProp.getParams("ALARM_SERVICE_SENDURL");
    if (StringUtils.isOrBlank(sPort, rootUrl, sendUrl))
      throw new IllegalArgumentException("alarm config can't be null");
    String sTimeOut = ConfigProp.getParams("ALARM_SERVICE_TIMEOUT");
    String sRetryTime = ConfigProp.getParams("ALARM_SERVICE_RETRYTIME");
    int port = Integer.valueOf(sPort);
    int timeout = StringUtils.isNotBlank(sTimeOut) ? Integer.valueOf(sTimeOut) : 20000;
    int retryTime = StringUtils.isNotBlank(sRetryTime) ? Integer.valueOf(sRetryTime) : 3;
    HttpUtil http = HttpUtil.getInstance(rootUrl, port, "http", timeout, retryTime);
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    http.setMethod(HttpMethodUtil.getPostMethod(sendUrl, entity.getParams()));// 使用POST方式提交数据
    try {
      http.getHttpResult();
    } catch (Exception e) {
      LoggerUtil.error("调用报警接口异常", e);
    }
  }
}
