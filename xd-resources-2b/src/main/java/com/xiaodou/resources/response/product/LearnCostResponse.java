package com.xiaodou.resources.response.product;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class LearnCostResponse extends BaseResponse {

  public LearnCostResponse(ResultType resultType) {
    super(resultType);
  }

  public LearnCostResponse(ProductResType resultType) {
    super(resultType);
  }

  private String learnRecordId;// 学习记录id
  

  public String getLearnRecordId() {
    return learnRecordId;
  }

  public void setLearnRecordId(String learnRecordId) {
    this.learnRecordId = learnRecordId;
  }

}
