package com.xiaodou.oms.entity.message;

public class FraudPushMessage {
  private String params;
  private int hasedRetry;
  public String getParams() {
    return params;
  }
  public void setParams(String params) {
    this.params = params;
  }
  public int getHasedRetry() {
    return hasedRetry;
  }
  public void setHasedRetry(int hasedRetry) {
    this.hasedRetry = hasedRetry;
  }
}
