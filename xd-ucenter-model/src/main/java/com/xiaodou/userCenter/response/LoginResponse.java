package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class LoginResponse extends BaseResultInfo {
  public LoginResponse(UcenterResType type) {
    super(type);
  }

  /**
   * 用户token值
   */
  private String token;

  public LoginResponse(ResultType type) {
    super(type);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
