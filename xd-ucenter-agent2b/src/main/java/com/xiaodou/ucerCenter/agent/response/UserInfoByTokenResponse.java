package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class UserInfoByTokenResponse extends BaseResultInfo {
  private UserModelResponse user;

  public UserInfoByTokenResponse() {}

  public UserInfoByTokenResponse(ResultType type) {
    super(type);
  }

  public UserInfoByTokenResponse(UcenterResType type) {
    super(type);
  }

  public UserModelResponse getUser() {
    return user;
  }

  public void setUser(UserModelResponse user) {
    this.user = user;
  }

}
