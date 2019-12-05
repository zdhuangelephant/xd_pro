package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserContinuousLearnDaysResponse extends BaseResponse {

  public UserContinuousLearnDaysResponse() {}

  public UserContinuousLearnDaysResponse(ResultType resultType) {
    super(resultType);
  }

  private String learnDays;

  public String getLearnDays() {
    return learnDays;
  }

  public void setLearnDays(String learnDays) {
    this.learnDays = learnDays;
  }


}
