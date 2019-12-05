package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class UserInfoByTokenResponse extends BaseResultInfo {
  private UserModelResponse user;

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
