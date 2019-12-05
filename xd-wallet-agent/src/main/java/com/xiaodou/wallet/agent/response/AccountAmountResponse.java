package com.xiaodou.wallet.agent.response;



public class AccountAmountResponse extends ResponseBase {

  public AccountAmountResponse() {}

  /** accountAmount 账户余额 */
  private Double accountAmount = 0d;


  public Double getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(Double accountAmount) {
    this.accountAmount = accountAmount;
  }

}
