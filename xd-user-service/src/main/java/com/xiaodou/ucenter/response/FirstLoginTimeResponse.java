package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

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
