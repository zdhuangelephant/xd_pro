package com.xiaodou.resources.enums.product;


public enum BuyProductStatus {

  PURCHASE("0", "已购买"), NOPURCHASE("1", "未购买");


  private String statusId;
  private String statusDesc;

  private BuyProductStatus(String statusId, String statusDesc) {
    this.statusId = statusId;
    this.statusDesc = statusDesc;
  }

  public String getStatusId() {
    return statusId;
  }

  public String getStatusDesc() {
    return statusDesc;
  }
}
