package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class CheckPhoneResponse extends BaseResponse {
  
  public CheckPhoneResponse(){}

  public CheckPhoneResponse(ResultType resType) {
    super(resType);
  }

  public CheckPhoneResponse(UcenterResType resType) {
    super(resType);
  }

  private String isRegisted;

  public String getIsRegisted() {
    return isRegisted;
  }

  public void setIsRegisted(String isRegisted) {
    this.isRegisted = isRegisted;
  }

}
