package com.xiaodou.server.pay.payplatform;

import com.xiaodou.server.pay.request.PayRequest;


public interface IPayPlatform {
  /**
   * 支付平台支付订单方法 </br> CA平台 : 调用钱包支付该交易单</br> DC平台 : 去相关三方支付平台进行下单操作
   */
  public <T extends IPayOrderResponse> T payOrder(PayRequest request);
}
