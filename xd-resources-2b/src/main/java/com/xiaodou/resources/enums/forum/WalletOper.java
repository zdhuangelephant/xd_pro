package com.xiaodou.resources.enums.forum;

/**
 * @name @see com.xiaodou.wallet.enums.WalletOper.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包操作类型
 * @version 1.0
 */
public enum WalletOper {

  Order(0, "订单"), Pay(1, "支出"), Recharge(2, "充值"), GiftOrder(3, "赞赏订单"), GiftPay(4, "赞赏完成"), ClearIncome(
      5, "赞赏结算"), ExtractMoneyOrder(6, "提现订单"), ExtractMoneyPay(7, "提现完成");
  WalletOper(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private int code;
  private String desc;

  public int getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
