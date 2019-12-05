package com.xiaodou.wallet.agent.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.wallet.agent.model.AccountWalletBill;

/**
 * @name AccountBillResponse 
 * CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 用户账单响应类
 * @version 1.0
 */
public class AccountBillResponse extends ResponseBase {
  public AccountBillResponse() {}

  /** billList 账单列表 */
  private List<AccountWalletBill> billList = Lists.newArrayList();

  public List<AccountWalletBill> getBillList() {
    return billList;
  }

  public void setBillList(List<AccountWalletBill> billList) {
    this.billList = billList;
  }

}
