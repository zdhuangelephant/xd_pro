package com.xiaodou.payment.util;

import com.xiaodou.payment.constant.PaymentConfig;

public class PaymentProviderUtil {

  public static Integer getPaymentProvider(String key) {
    key = key.toUpperCase();
    switch (key) {
      case "ALIPAY":
        return PaymentConfig.PAYMENT_PROVIDER_ALIPAY;
      case "TENPAY":
        return PaymentConfig.PAYMENT_PROVIDER_TENPAY;
      case "KUAIQIAN":
        return PaymentConfig.PAYMENT_PROVIDER_KUAIQIAN;
      case "JSYH":
        return PaymentConfig.PAYMENT_PROVIDER_JSYH;
      case "GSYH":
        return PaymentConfig.PAYMENT_PROVIDER_GSYH;
      case "ZGYH":
        return PaymentConfig.PAYMENT_PROVIDER_ZGYH;
      case "NYYH":
        return PaymentConfig.PAYMENT_PROVIDER_NYYH;
      case "JTYH":
        return PaymentConfig.PAYMENT_PROVIDER_JTYH;
      case "ZSYH":
        return PaymentConfig.PAYMENT_PROVIDER_ZSYH;
      case "UNION":
        return PaymentConfig.PAYMENT_PROVIDER_UNION;
      case "PAYPAL":
        return PaymentConfig.PAYMENT_PROVIDER_PAYPAL;
      case "WXPAY":
        return PaymentConfig.PAYMENT_PROVIDER_WXPAY;
      case "APPLEPAY":
        return PaymentConfig.PAYMENT_PROVIDER_APPLEPAY;
      default:
        return -1;
    }
  }

}
