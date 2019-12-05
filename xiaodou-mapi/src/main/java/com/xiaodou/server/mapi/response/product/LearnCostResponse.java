package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class LearnCostResponse extends BaseResponse {

  public LearnCostResponse() {}

  public LearnCostResponse(ResultType resultType) {
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
