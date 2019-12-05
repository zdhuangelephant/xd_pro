package com.xiaodou.common.test.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;

public class HttpUtilTest {

  /**
   * 测试GetHttpResult方法
   * 
   * @throws UnsupportedEncodingException
   */
  @Test
  public void testGetHttpResult() throws UnsupportedEncodingException {
    HttpUtil http = HttpUtil.getInstance("www.baidu.com", 443, "https");
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    // String serUrl =
    // "/pur/train/number.json?date=2014-10-13&from_station_name=%E5%93%88%E5%B0%94%E6%BB%A8%E4%B8%9C&partner_id=00002&to_station_name=%E5%93%88%E5%B0%94%E6%BB%A8&train_type=0&sign=lUYSGD%2FeB5%2FeBEFX9cdrt5Bl13N9H3YlOgsYPfrFW1qT4UznZRL1xIt7XE%2FH8IFTk48dkd4exAXPu7vV5akW%2FyzqxdlJpZBW2ZNg7HH6zC8dlDfWeS2sOuo09lQiQKNwgeaslUUxLhCQclp66t07RP5Dhe6nrXMqflgLgAyIn%2BA%3D";
    // GetMethod getMethod = HttpMethodUtil
    // .getGetMethod(serUrl);
    // HttpMethodParams httpMethodParams = new HttpMethodParams();
    // httpMethodParams.setContentCharset("utf8");
    // getMethod.setParams(httpMethodParams);
    // http.setMethod(getMethod);// 使用GET方式提交数据
    String baseUrl =
        "/s?wd=qwe&rsv_spt=1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=3&rsv_sug4=133&rsv_sug1=2&rsv_sug2=0&inputT=744";
    http.setMethod(HttpMethodUtil.getGetMethod(baseUrl));
    // http.setMethod(HttpMethodUtil.getPostMethod(baseurl,
    // "application/json", "utf-8", jsonStr));
    HttpResult result = http.getHttpResult();
    System.out.println(result);
  }

  public static void main(String[] args) {
    HttpUtil http = HttpUtil.getInstance("localhost", 80, "http");
    http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    String baseUrl = "/message/messageConsume";
    NameValuePair[] params = new NameValuePair[3];
    params[0] = new NameValuePair("tag", "92c5731ec7-fb5d-404d-9814-ccb9367f2f2b");
    params[1] = new NameValuePair("messageName", "2_mission_event");
    params[2] =
        new NameValuePair(
            "message",
            "{\"eventTag\":\"92c5731ec7-fb5d-404d-9814-ccb9367f2f2b\",\"eventType\":\"TollgateEvent\",\"eventMessage\":\"{\\\"chapterId\\\":\\\"10289232\\\",\\\"count\\\":2,\\\"courseId\\\":\\\"10289153\\\",\\\"credit\\\":2,\\\"majorId\\\":\\\"59\\\",\\\"module\\\":\\\"2\\\",\\\"score\\\":100,\\\"starLevel\\\":3,\\\"tollgateId\\\":\\\"10289229\\\",\\\"userId\\\":\\\"1257\\\"}\"}");
    http.setMethod(HttpMethodUtil.getPostMethod(baseUrl, params));
    HttpResult result = http.getHttpResult();
    System.out.println(result);
  }

}
