package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class QueryUserRequest extends BaseRequest {

  @NotEmpty
  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
