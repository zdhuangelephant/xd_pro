package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class LoginResponse extends BaseResultInfo {

  public LoginResponse() {}

  public LoginResponse(UcenterResType type) {
    super(type);
  }

  /**
   * 用户token值
   */
  private String token = StringUtils.EMPTY;

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
