package com.xiaodou.wallet.response.wallet;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.response.BaseResponse;
import com.xiaodou.wallet.response.WalletResType;

public class AccountAmountResponse extends BaseResponse {

  public AccountAmountResponse() {}

  public AccountAmountResponse(ResultType type) {
    super(type);
  }

  public AccountAmountResponse(WalletResType type) {
    super(type);
  }

  /** accountAmount 账户余额 */
  private Double accountAmount = 0d;

  public Double getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(Double accountAmount) {
    this.accountAmount = accountAmount;
  }

}
