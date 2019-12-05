package com.xiaodou.server.pay.request.callback;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class AppleCallbackRequest extends BaseValidatorPojo {
  @NotEmpty
  private String tradeNo;// 凭证号

  @NotEmpty
  private String thirdTositenumero;// 支付第三方凭证号

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getThirdTositenumero() {
    return thirdTositenumero;
  }

  public void setThirdTositenumero(String thirdTositenumero) {
    this.thirdTositenumero = thirdTositenumero;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
