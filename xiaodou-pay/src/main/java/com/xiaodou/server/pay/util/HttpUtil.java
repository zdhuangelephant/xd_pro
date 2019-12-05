package com.xiaodou.server.pay.util;

import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.pay.prop.VerifyPayProp;

public class HttpUtil {

  /**
   * 验证购买凭证
   * 
   * @param paramKey url需要传的参数名
   * @param paramValue url需要传的参数值
   * @param clazzRes 返回参数反序列化bean的class对象
   * @return
   */
  public static <T> T doflowVerifyPayTos(String payType, String thirdTositenumero_key,
      String thirdTositenumero_value, Class<T> clazzRes) {
    try {
      String content = "{\"" + thirdTositenumero_key + "\":\"" + thirdTositenumero_value + "\"}";
      String resultJson =
          HttpWrapper.send(VerifyPayProp.getParams("verify.pay.applePay.protocol"),
              VerifyPayProp.getParams(String.format("verify.pay.applePay.host.%s", payType)),
              Integer.parseInt(VerifyPayProp.getParams("verify.pay.applePay.port")),
              VerifyPayProp.getParams("verify.pay.applePay.sendVerifyUrl"),
              Integer.parseInt(VerifyPayProp.getParams("verify.pay.applePay.retryTime")),
              Integer.parseInt(VerifyPayProp.getParams("verify.pay.applePay.timeout")), content,
              null);
      return parseResponse(resultJson, clazzRes);
    } catch (Exception e) {
      e.printStackTrace();
      LoggerUtil.error("[验证applePay购买凭证异常]", e);
      return null;
    }
  }

  /**
   * 构造request请求内容
   * 
   * @param obj
   * @param clazzReq
   * @return
   * @throws Exception
   */
  public static String createContent(Object obj) throws Exception {
    if (obj == null) return StringUtils.EMPTY;
    StringBuilder content = new StringBuilder();
    String value = JSON.toJSONString(obj);
    content.append("&").append("req=").append(URLEncoder.encode(value, "utf-8"));
    return content.substring(1);

  }

  public static <T> T parseResponse(String result, Class<T> clazzRes) {
    return null == result ? null : FastJsonUtil.fromJson(result, clazzRes);
  }

  @SuppressWarnings("unused")
  private static class VerifyPayTos {

    private String ThirdTositenumero_key;// 凭证名
    private String ThirdTositenumero_value;// 凭证值

    public VerifyPayTos() {}

    public VerifyPayTos(String thirdTositenumero_key, String thirdTositenumero_value) {
      super();
      ThirdTositenumero_key = thirdTositenumero_key;
      ThirdTositenumero_value = thirdTositenumero_value;
    }

    public String getThirdTositenumero_key() {
      return ThirdTositenumero_key;
    }

    public void setThirdTositenumero_key(String thirdTositenumero_key) {
      ThirdTositenumero_key = thirdTositenumero_key;
    }

    public String getThirdTositenumero_value() {
      return ThirdTositenumero_value;
    }

    public void setThirdTositenumero_value(String thirdTositenumero_value) {
      ThirdTositenumero_value = thirdTositenumero_value;
    }
  }
}
