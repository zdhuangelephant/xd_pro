package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class UserModelResponse extends BaseResponse {

  public UserModelResponse() {}

  public UserModelResponse(ResultType type) {
    super(type);
  }

  public UserModelResponse(UcenterResType type) {
    super(type);
  }

  private UserModel user;

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

}
