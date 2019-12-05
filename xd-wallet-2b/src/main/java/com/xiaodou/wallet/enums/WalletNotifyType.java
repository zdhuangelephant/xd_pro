package com.xiaodou.wallet.enums;

public enum WalletNotifyType {
  
  PayNotify(1, "充值回调"),GiftNotify(2,"赞赏回调");
  
  WalletNotifyType(int code, String desc) {
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
