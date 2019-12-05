package com.xiaodou.wallet.agent.response;


/**
 * @name @see com.xiaodou.wallet.response.wallet.RechargeOrderResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月6日
 * @description 充值余额订单响应
 * @version 1.0
 */
public class RechargeOrderResponse extends ResponseBase {

  /** billNo 账单编号 */
  private Long billNo;
  /** tradeNo 外部交易号 */
  private String tradeNo;

  public Long getBillNo() {
    return billNo;
  }

  public void setBillNo(Long billNo) {
    this.billNo = billNo;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

}
