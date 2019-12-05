package com.xiaodou.server.pay.payplatform;

import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.request.PayRequest;


public class PlatformHelper {

  public static PlatformFilter buildFilter(PayRequest pojo) {
    PlatformFilter filter = new PlatformFilter();
    switch (pojo.getMixpaymentType().toString()) {
      case PayConstant.MIX_PAYMENT_CA:
        filter.setCaPlatform(PayPlatformFactory.chooseCaPlatform());
        break;
      case PayConstant.MIX_PAYMENT_CA_CC:
        filter.setCaPlatform(PayPlatformFactory.chooseCaPlatform());
        break;
      case PayConstant.MIX_PAYMENT_CA_DC:
        filter.setCaPlatform(PayPlatformFactory.chooseCaPlatform());
        if (null != pojo.getDc())
          filter.setDcPlatform(PayPlatformFactory.chooseDcPlatform(pojo.getDc()
              .getPaymentProviderId()));
        break;
      case PayConstant.MIX_PAYMENT_CC:
        break;
      case PayConstant.MIX_PAYMENT_DC:
        if (null != pojo.getDc())
          filter.setDcPlatform(PayPlatformFactory.chooseDcPlatform(pojo.getDc()
              .getPaymentProviderId()));
        break;
    }
    return filter;
  }

}
