package com.xiaodou.server.pay.enums;

public enum ApplePayType {
  SANDBOX("sandbox"), BUY("buy");
  ApplePayType(String payType) {
    this.payType = payType;
  }

  private String payType;

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }
}
