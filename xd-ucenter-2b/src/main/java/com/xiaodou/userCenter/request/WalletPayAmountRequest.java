package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.userCenter.request.WalletPayAmountRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月7日
 * @description 钱包发起支付请求
 * @version 1.0
 */
public class WalletPayAmountRequest extends BaseRequest {
  /** amount 实际扣款金额 */
  @NotEmpty
  private Double amount;
  /** tradeNo 外部交易号 */
  @NotEmpty
  private String tradeNo;

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }
}
