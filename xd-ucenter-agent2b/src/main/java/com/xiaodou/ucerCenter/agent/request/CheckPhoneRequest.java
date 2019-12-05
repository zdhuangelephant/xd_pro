package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class CheckPhoneRequest extends BaseRequest {
  /**
   * 待注册手机号
   */
  @NotEmpty
  private String phoneNum;

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

}
