package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class CostLearnTimeResponse extends BaseResponse {

  public CostLearnTimeResponse(ResultType resultType) {
    super(resultType);
  }

  public CostLearnTimeResponse(ProductResType resultType) {
    super(resultType);
  }

  // 学习时长
  private String learnTime;

  public String getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(String learnTime) {
    this.learnTime = learnTime;
  }
}
