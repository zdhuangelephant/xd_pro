package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserInfoByTokenResponse extends BaseResponse {
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
