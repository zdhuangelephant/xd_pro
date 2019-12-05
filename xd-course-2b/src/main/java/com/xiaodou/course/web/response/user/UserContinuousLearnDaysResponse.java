package com.xiaodou.course.web.response.user;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserContinuousLearnDaysResponse extends BaseResponse {

  public UserContinuousLearnDaysResponse() {}

  public UserContinuousLearnDaysResponse(ResultType resultType) {
    super(resultType);
  }

  public UserContinuousLearnDaysResponse(ProductResType resultType) {
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
