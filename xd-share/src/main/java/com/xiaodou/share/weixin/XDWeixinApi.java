package com.xiaodou.share.weixin;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

public class XDWeixinApi {
  private static final String accessTokenKey = "xiaodou:share:weixin:accesstoken";
  private static final String jsapiTicketKey = "weixin_share_jsapi_ticket";
  private static int WEIXINSECOND = 7000;

  public static String getWeiXinAccessToken() {
    String accessToken = StringUtils.EMPTY;
    accessToken = JedisUtil.getStringFromJedis(accessTokenKey);
    if (null != accessToken) return accessToken;
    String url = WeiXinProp.getParams("xiaodou.weixin.share.accessToken");
    HttpClient client = new HttpClient();
    client.getParams().setContentCharset("UTF-8");
    GetMethod method = new GetMethod(url);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    try {
      client.executeMethod(method);
      String urlResult = method.getResponseBodyAsString();
      JSONObject json = JSON.parseObject(urlResult);
      String token = json.getString("access_token");
      accessToken = token;
      // 添加缓存
      JedisUtil.addStringToJedis(accessTokenKey, accessToken, WEIXINSECOND);
    } catch (HttpException e) {
      LoggerUtil.error("HttpException", e);
      accessToken = "-1";
    } catch (IOException e) {
      LoggerUtil.error("IOException", e);
      accessToken = "-1";
    }
    return accessToken;
  }

  public String getWeiXinJsApiTicket() {
    String jsapi_ticket = StringUtils.EMPTY;
    String accessToken = getWeiXinAccessToken();
    jsapi_ticket = JedisUtil.getStringFromJedis(jsapiTicketKey);
    if (null != jsapi_ticket) return jsapi_ticket;
    String url = WeiXinProp.getParams("xiaodou.weixin.share.weiXinJsApiTicket") + accessToken;
    HttpClient client = new HttpClient();
    client.getParams().setContentCharset("UTF-8");
    GetMethod method = new GetMethod(url);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    try {
      client.executeMethod(method);
      String urlResult = method.getResponseBodyAsString();
      JSONObject json = JSON.parseObject(urlResult);
      String ticket = json.getString("ticket");
      jsapi_ticket = ticket;
      // 添加缓存
      JedisUtil.addStringToJedis(jsapiTicketKey, jsapi_ticket, WEIXINSECOND);
    } catch (HttpException e) {
      LoggerUtil.error("HttpException", e);
      jsapi_ticket = "-1";
    } catch (IOException e) {
      LoggerUtil.error("IOException", e);
      jsapi_ticket = "-1";
    }
    return jsapi_ticket;
  }

}
