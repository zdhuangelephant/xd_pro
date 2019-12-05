package com.xiaodou.payment.vo.response;


/**
 * 获取tradeNo返回信息
 * 
 * @author Jiejun.Gao, tian.dong
 */
public class TokenResponse extends ResponseBase {
  private String tradeNo;

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }
}
