package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UpTokenResponse extends BaseResponse {
  public UpTokenResponse() {}

  public UpTokenResponse(ResultType type) {
    super(type);
  }

  private String upToken;

  public String getUpToken() {
    return upToken;
  }

  public void setUpToken(String upToken) {
    this.upToken = upToken;
  }

  public UpTokenResponse(UcenterResType type) {
    super(type);
  }
}
