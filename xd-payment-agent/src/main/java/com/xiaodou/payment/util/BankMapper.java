package com.xiaodou.payment.util;

/**
 * 银行卡信息
 *
 * @author Jiejun.Gao
 */
public class BankMapper {
  private String bankName;
  private String myCode;
  private String payCode;

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getMyCode() {
    return myCode;
  }

  public void setMyCode(String myCode) {
    this.myCode = myCode;
  }

  public String getPayCode() {
    return payCode;
  }

  public void setPayCode(String payCode) {
    this.payCode = payCode;
  }
}
