package com.xiaodou.ucenter.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class LoginResponse extends BaseResponse {

  public LoginResponse() {}

  public LoginResponse(UcenterResType type) {
    super(type);
  }

  public LoginResponse(ResultType type) {
    super(type);
  }

  /**
   * 用户token值
   */
  private String token = StringUtils.EMPTY;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
