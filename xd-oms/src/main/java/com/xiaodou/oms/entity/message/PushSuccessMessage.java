package com.xiaodou.oms.entity.message;

public class PushSuccessMessage {
  private int retryTime;
  private String id;
  private String content;
  public int getRetryTime() {
    return retryTime;
  }
  public void setRetryTime(int retryTime) {
    this.retryTime = retryTime;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
}
