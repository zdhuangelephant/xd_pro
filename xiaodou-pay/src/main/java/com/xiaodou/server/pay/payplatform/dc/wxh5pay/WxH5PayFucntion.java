package com.xiaodou.server.pay.payplatform.dc.wxh5pay;

import java.util.SortedMap;
import java.util.TreeMap;

import com.xiaodou.server.pay.constant.WeixinPayConstants;
import com.xiaodou.server.pay.payplatform.dc.wxpay.WxPayDto;

public class WxH5PayFucntion {

  public static String confirm(WxPayDto tpWxPay) {
    String timeStamp = PayCommonUtil.create_timestamp();
    String nonceStr = PayCommonUtil.create_nonce_str();
    String prepayId = WxH5PayUtil.unifiedorder(tpWxPay);
    SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
    signParams.put("appId", WeixinPayConstants.getH5AppId(tpWxPay.getBusinessType()));
    signParams.put("nonceStr", nonceStr);
    signParams.put("package", "prepay_id=" + prepayId);
    signParams.put("timeStamp", timeStamp);
    signParams.put("signType", "MD5");
    // 生成支付签名，要采用URLENCODER的原始值进行SHA1算法！
    String sign = PayCommonUtil.createSign(tpWxPay.getBusinessType(), signParams);
    String packages = "prepay_id=" + prepayId;
    String finaPackage =
        "\"appId\":\"" + WeixinPayConstants.getH5AppId(tpWxPay.getBusinessType())
            + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + nonceStr
            + "\",\"package1\":\"" + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
            + sign + "\"";
    return finaPackage;
  }
}
