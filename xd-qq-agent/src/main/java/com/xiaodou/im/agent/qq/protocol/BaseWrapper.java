package com.xiaodou.im.agent.qq.protocol;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.constant.Http;
import com.xiaodou.im.agent.qq.constant.TransType;
import com.xiaodou.im.agent.qq.request.BaseRequest;
import com.xiaodou.im.agent.qq.util.UrlRedisUtil;

/**
 * Date: 2014/12/11
 * Time: 14:12
 *
 * @author Tian.Dong
 */
public class BaseWrapper {
  public String http(BaseRequest request) throws Exception {
    String url = request.getUrl();
    String params = request.getParams();
    if (request.getHttpMethod().equals(Http.GET)) {
      return doGet(url, params);
    } else if (request.getHttpMethod().equals(Http.POST)) {
      return doPost(url, params);
    }
    return null;
  }

  /**
   * Origin: http://s.web2.qq.com
   * User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36
   * Content-Type: application/x-www-form-urlencoded
   * <p/>
   * DNT: 1
   * Referer: http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1
   */
  private String doPost(String url, String params) throws UnsupportedEncodingException {
    PostMethod method = HttpMethodUtil.getPostMethod(url, "application/x-www-form-urlencoded", "utf-8", params);
    fillMethod(method);

    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(70000);
    httpUtil.setMethod(method);
    HttpResult httpResult = httpUtil.getHttpResult();
    logInOut(url, params, httpResult);
    return httpResult.getContent();
  }

  private void fillMethod(HttpMethod method) {
    method.setRequestHeader("Cookie", getCookie());
    method.setRequestHeader("Connection", "keep-alive");
    method.setRequestHeader("Origin", "http://s.web2.qq.com");
    method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36");
    String refer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
    if (UrlRedisUtil.getREFER_URL() != null) {
      refer = UrlRedisUtil.getREFER_URL();
    }
    method.setRequestHeader("Referer", refer);
    method.setRequestHeader("Accept-Encoding", "gzip, deflate");
    method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
  }

  private String doGet(String url, String params) {
    url = url + "?" + params;
    GetMethod method = HttpMethodUtil.getGetMethod(url);
    fillMethod(method);

    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(70000);
    httpUtil.setMethod(method);
    HttpResult httpResult = httpUtil.getHttpResult();
    logInOut(url, params, httpResult);
    return httpResult.getContent();
  }

  private void logInOut(String url, String params, HttpResult httpResult) {
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl(url);
    entity.setParams(params);
    entity.setResult(httpResult);
    LoggerUtil.inOutInfo(entity);
  }

  public <T> T transResponse(String res, Class<T> responseClass, TransType responseType) {
    return FastJsonUtil.fromJson(res, responseClass);
  }

  public String getCookie() {
    return cookie;
  }

  private String cookie = "ng_stng_st_sid_v2=9KRr3Qkh7U0Z-FqjV_UuOg..; qm_username=23441099; qm_sid=59760fe442b7a2bc191859354c195f6d,qWUhqMFUqYVc2QjZFWGJJelJzcHVnTFIwWmN5QVNuVk9rTkp6N25CclhjUV8.; RK=susOz9wgMj; verifysession=h02D6EDVSa0OQitfw-1Xz0uBH8t0H9UVSIz8LKerHhTCDDVu3UOsh-sFiSVnl_vnf4cN4v0yVPeyidG9Mz3IJ10Cw**; ptui_loginuin=1209440675; ptisp=cnc; ptcz=6159f7af4ba7817c5d199fdb431d51274d96ad66e6e42be571389a7a1669c2a6; pt2gguin=o1209440675; uin=o1209440675; skey=@bu7yOJxhv; p_uin=o1209440675; p_skey=GMRc8lwxqScnyC9xQddF9x0dh4iZS-fWN4*zNt2xW1I_; pt4_token=iu9mQVdrQ4il2URc3rEt-A__; ts_last=web2.qq.com/; ts_uid=2208717513; pgv_info=ssid=s5917808426&pgvReferrer=; pgv_pvid=1599739387; o_cookie=1209440675; ptwebqq=a602469c90ba602215eced8e401eac79b9b9c04fd10dfe183e3b2955c9dff089";

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }
}
