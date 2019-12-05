package com.xiaodou.ucenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class FuzzySeekingUserRequest extends BaseRequest {

  @NotEmpty
  private String nickName;

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
