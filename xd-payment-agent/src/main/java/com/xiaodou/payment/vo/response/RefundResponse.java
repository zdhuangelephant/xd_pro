package com.xiaodou.payment.vo.response;


/**
 * 退款返回信息
 *
 * @author Jiejun.Gao, tian.dong
 */
public class RefundResponse extends ResponseBase{

  private String payUrl;

  private String payStatus;

  private String payInfo;

  private String bankName;

  private String processDays;

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

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getProcessDays() {
    return processDays;
  }

  public void setProcessDays(String processDays) {
    this.processDays = processDays;
  }
}
