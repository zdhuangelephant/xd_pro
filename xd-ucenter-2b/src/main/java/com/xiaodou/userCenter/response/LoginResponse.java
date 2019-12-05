package com.xiaodou.userCenter.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class LoginResponse extends BaseResultInfo {

  public LoginResponse() {}

  public LoginResponse(UcenterResType type) {
    super(type);
  }

  public LoginResponse(ResultType type) {
    super(type);
  }

  /** user 用户模型 */
  private BaseUserModel user;
  /**
   * 用户token值
   */
  private String token = StringUtils.EMPTY;

  public BaseUserModel getUser() {
    return user;
  }

  public void setUser(BaseUserModel user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
