package com.xiaodou.server.pay.payplatform;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.payplatform.ca.CaPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.alipay.AlipayPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.applepay.ApplePayPlatform;
import com.xiaodou.server.pay.payplatform.dc.wxh5pay.WxH5PayPlatform;
import com.xiaodou.server.pay.payplatform.dc.wxpay.WxpayPayPlatform;

class PayPlatformFactory {

  private static final Map<String, IPayPlatform> platformMap = Maps.newHashMap();
  static {
    platformMap.put("CA", new CaPayPlatform());
    platformMap.put("DC_4201", new AlipayPayPlatform()); // Alipay
    platformMap.put("DC_4202", null); // Tenpay
    platformMap.put("DC_4203", null); // 为快钱预留，可以暂时不考虑
    platformMap.put("DC_4204", null); // 建设银行
    platformMap.put("DC_4205", null); // 工商银行(取得 url 用 POST 方式到工行)
    platformMap.put("DC_4206", null); // 中国银行
    platformMap.put("DC_4207", null); // 农业银行
    platformMap.put("DC_4208", null); // 交通银行(取得 url 用 POST 方式到交行)
    platformMap.put("DC_4211", null); // 招商银行(取得 url 用 POST 方式到招行)
    platformMap.put("DC_4212", null); // 银联
    platformMap.put("DC_4213", null); // Paypal
    platformMap.put("DC_4222", new WxpayPayPlatform()); // 微信支付
    platformMap.put("DC_4231", new ApplePayPlatform()); // 苹果支付
    platformMap.put(String.format("DC_%s", PayConstant.PAYMETHOD_WX_OFFICIAL_JS), new WxH5PayPlatform());// 微信公众账号 JS 方式
  }

  public static CaPayPlatform chooseCaPlatform() {
    return (CaPayPlatform) platformMap.get("CA");
  }

  public static IDcPayPlatform chooseDcPlatform(short paymentproviderid) {
    return (IDcPayPlatform) platformMap.get(String.format("DC_%s", paymentproviderid));
  }

}
