package com.xiaodou.wallet.response.wallet;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.model.AccountWalletBill;
import com.xiaodou.wallet.response.BaseResponse;
import com.xiaodou.wallet.response.WalletResType;

/**
 * @name AccountBillResponse 
 * CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 用户账单响应类
 * @version 1.0
 */
public class AccountBillResponse extends BaseResponse {
  public AccountBillResponse() {}

  public AccountBillResponse(ResultType type) {
    super(type);
  }

  public AccountBillResponse(WalletResType type) {
    super(type);
  }

  /** billList 账单列表 */
  private List<AccountWalletBill> billList = Lists.newArrayList();

  public List<AccountWalletBill> getBillList() {
    return billList;
  }

  public void setBillList(List<AccountWalletBill> billList) {
    this.billList = billList;
  }

}
