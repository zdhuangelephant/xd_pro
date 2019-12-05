package com.xiaodou.resources.response.push;

public class PushResult {
  /**
   * 发送状态 0：成功  -1：失败
   */
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
