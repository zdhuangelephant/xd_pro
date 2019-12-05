package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class MyInfoResponse extends BaseResultInfo {

  public MyInfoResponse() {}

  public MyInfoResponse(UcenterResType type) {
    super(type);
  }

  public MyInfoResponse(ResultType type) {
    super(type);
  }

  private UserModelResponse user;

  public UserModelResponse getUser() {
    return user;
  }

  public void setUser(UserModelResponse user) {
    this.user = user;
  }
}
