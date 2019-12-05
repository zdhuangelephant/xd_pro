package com.xiaodou.common.info.ding;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.common.info.ding.req.DingSender.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知消息发送器
 * @version 1.0
 */
public class DingSender {

  /**
   * 发送钉钉通知消息
   * 
   * @param req 参数包装类
   */
  public static void send(IDingReq req) {
    if (null == req || StringUtils.isBlank(req.info()) || null == req.getUrls()
        || req.getUrls().isEmpty()) {
      return;
    }
    String reqInfo = req.info();
    List<String> urls = req.getUrls();
    for (String url : urls) {
      send(url, reqInfo);
    }
  }

  /**
   * 钉钉机器人发送通知消息
   * 
   * @param url 机器人
   * @param reqInfo 通知消息内容
   */
  private static void send(String url, String reqInfo) {
    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(5000);
    httpUtil.setRetryTime(2);
    HttpMethod postMethod = null;
    try {
      postMethod = HttpMethodUtil.getPostMethod(url, "application/json", "utf-8", reqInfo);
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.error("不支持编码格式", e);
    }
    if (null == postMethod) {
      LoggerUtil.error("http 失败", new RuntimeException());
      return;
    }
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    DingRes response = null;
    if (result.isResultOk()) {
      response = FastJsonUtil.fromJson(result.getContent(), DingRes.class);
    }
    if (null == response || StringUtils.isBlank(response.getErrcode())) {
      LoggerUtil.error("http 失败", new RuntimeException());
    } else {
      if (!response.getErrcode().equals("0")) {
        LoggerUtil.error(response.getErrmsg(), new RuntimeException());
      }
    }
  }

}
