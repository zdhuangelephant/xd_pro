package com.xiaodou.server.pay.payplatform.dc;

import com.xiaodou.server.pay.payplatform.IPayPlatform;
import com.xiaodou.server.pay.request.PayRequest;

public interface IDcPayPlatform extends IPayPlatform {

  @SuppressWarnings("unchecked")
  public DcPayOrderResponse payOrder(PayRequest request);

  public DcPayStatusResponse payStatus(String tradeNo);

}
