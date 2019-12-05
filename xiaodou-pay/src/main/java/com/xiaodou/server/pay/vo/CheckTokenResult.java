package com.xiaodou.server.pay.vo;

import com.xiaodou.server.pay.response.resultType.PaymentResType;

public class CheckTokenResult {

  private PaymentResType resultType;

  public boolean isCheckedOk() {
    return null == resultType;
  }

  public PaymentResType getResultType() {
    return resultType;
  }

  public void setResultType(PaymentResType resultType) {
    this.resultType = resultType;
  }

}
