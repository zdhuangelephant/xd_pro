package com.xiaodou.ucenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class FirstLoginTimeRequest extends BaseRequest {

  /** userId 用户ID */
  @NotEmpty
  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
