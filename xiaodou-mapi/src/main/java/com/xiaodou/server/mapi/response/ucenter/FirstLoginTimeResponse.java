package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class FirstLoginTimeResponse extends BaseResponse {
  public FirstLoginTimeResponse() {}

  public FirstLoginTimeResponse(ResultType type) {
    super(type);
  }

  public FirstLoginTimeResponse(UcenterResType type) {
    super(type);
  }

  private String fristLoginTime;

  public String getFristLoginTime() {
    return fristLoginTime;
  }

  public void setFristLoginTime(String fristLoginTime) {
    this.fristLoginTime = fristLoginTime;
  }

}
