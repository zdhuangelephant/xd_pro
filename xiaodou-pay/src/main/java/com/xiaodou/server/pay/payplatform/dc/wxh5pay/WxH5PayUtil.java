package com.xiaodou.server.pay.payplatform.dc.wxh5pay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;

import com.xiaodou.server.pay.constant.WeixinPayConstants;
import com.xiaodou.server.pay.payplatform.dc.wxpay.WxPayDto;

/**
 * 第六步：调用微信H5支付统一下单接口 得到预支付ID WxPayUtil
 * 
 * @author 李德洪
 * @date 2016年12月7日
 */
public class WxH5PayUtil {

  @SuppressWarnings("unchecked")
  public static String unifiedorder(WxPayDto tpWxPay) {
    SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
    parameters.put("appid", WeixinPayConstants.getH5AppId(tpWxPay.getBusinessType()));
    parameters.put("mch_id", WeixinPayConstants.getH5MchId(tpWxPay.getBusinessType()));
    parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
    parameters.put("body", tpWxPay.getBody());
    parameters.put("out_trade_no", tpWxPay.getOrderId());
    // 总金额以分为单位，不带小数点
    parameters.put("total_fee", getMoney(tpWxPay.getTotalFee()));
    parameters.put("spbill_create_ip", "127.0.0.1");
    parameters.put("notify_url", WeixinPayConstants.getNotifyUrl());
    parameters.put("trade_type", "JSAPI");
    parameters.put("openid", tpWxPay.getOpenId());
    String sign = PayCommonUtil.createSign(tpWxPay.getBusinessType(), parameters);
    parameters.put("sign", sign);
    String requestXML = PayCommonUtil.getRequestXml(parameters);
    System.out.println(requestXML.toString());
    String result =
        CommonUtil.httpsRequest(WeixinPayConstants.getUnifiedOrderUrl(), "POST", requestXML);
    System.out.println(result.toString());
    Map<String, String> map = new HashMap<String, String>();
    try {
      map = XMLUtil.doXMLParse(result);
    } catch (JDOMException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }// 解析微信返回的信息，以Map形式存储便于取值
    return map.get("prepay_id").toString();
  }


  /**
   * 元转换成分
   * 
   * @param money
   * @return
   */
  public static String getMoney(String amount) {
    if (amount == null) {
      return "";
    }
    // 金额转化为分为单位
    String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥ 或者$的金额
    int index = currency.indexOf(".");
    int length = currency.length();
    Long amLong = 0l;
    if (index == -1) {
      amLong = Long.valueOf(currency + "00");
    } else if (length - index >= 3) {
      amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
    } else if (length - index == 2) {
      amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
    } else {
      amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
    }
    return amLong.toString();
  }
}
