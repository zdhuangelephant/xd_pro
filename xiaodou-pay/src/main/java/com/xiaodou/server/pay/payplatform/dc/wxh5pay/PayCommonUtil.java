package com.xiaodou.server.pay.payplatform.dc.wxh5pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import com.xiaodou.server.pay.constant.WeixinPayConstants;

/**
 * 第五步：微信H5支付 签名 随机码 时间戳等管理 PayCommonUtil
 * 
 * @author 李德洪
 * @date 2016年12月7日
 */
public class PayCommonUtil {
  /**
   * 获取支付随机码
   * 
   * @return
   */
  public static String create_nonce_str() {
    return UUID.randomUUID().toString();
  }

  /**
   * 获取微信支付时间戳
   * 
   * @return
   */
  public static String create_timestamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }

  /**
   * 获取预支付ID时 获取随机码
   * 
   * @param length
   * @return
   */
  public static String CreateNoncestr(int length) {
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    String res = "";
    for (int i = 0; i < length; i++) {
      Random rd = new Random();
      res += chars.indexOf(rd.nextInt(chars.length() - 1));
    }
    return res;
  }

  /**
   * 获取预支付ID时 获取随机码
   * 
   * @param length
   * @return
   */
  public static String CreateNoncestr() {
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    String res = "";
    for (int i = 0; i < 16; i++) {
      Random rd = new Random();
      res += chars.charAt(rd.nextInt(chars.length() - 1));
    }
    return res;
  }

  /**
   * @author Mark
   * @Description：sign签名
   * @param characterEncoding 编码格式
   * @param parameters 请求参数
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static String createSign(String merchantId, SortedMap<Object, Object> parameters) {
    StringBuffer sb = new StringBuffer();
    Set es = parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      Object v = entry.getValue();
      if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + WeixinPayConstants.getH5ApiKey(merchantId));
    String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
    return sign;
  }


  /**
   * @author Mark
   * @Description：将请求参数转换为xml格式的string
   * @param parameters 请求参数
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static String getRequestXml(SortedMap<Object, Object> parameters) {
    StringBuffer sb = new StringBuffer();
    sb.append("<xml>");
    Set es = parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
        sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
      } else {
        sb.append("<" + k + ">" + v + "</" + k + ">");
      }
    }
    sb.append("</xml>");
    return sb.toString();
  }

  /**
   * @author Mark
   * @Description：返回给微信的参数
   * @param return_code 返回编码
   * @param return_msg 返回信息
   * @return
   */
  public static String setXML(String return_code, String return_msg) {
    return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA["
        + return_msg + "]]></return_msg></xml>";
  }

}
