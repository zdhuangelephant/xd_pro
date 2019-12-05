package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class CheckPhoneResponse extends BaseResultInfo {

  public CheckPhoneResponse() {}

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
