package com.xiaodou.payment.vo.response;


/**
 * 支付返回信息
 *
 * @author Jiejun.Gao,tian.dong
 */
public class PayResponse extends ResponseBase{

  private String payUrl;

  private String payStatus;

  private String payInfo;

  public String getPayUrl() {
    return payUrl;
  }

  public void setPayUrl(String payUrl) {
    this.payUrl = payUrl;
  }

  public String getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }
}
