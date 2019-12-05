package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class MyInfoResponse extends BaseResponse {
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
