package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.enums.ProduceResType;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserCoinRemainResponse extends BaseResponse {
  /* 购买课程后用户预计剩余金币 */
  private Integer remainCoin;

  public UserCoinRemainResponse(ResultType resultType) {
    super(resultType);
  }

  public UserCoinRemainResponse(ProduceResType resultType) {
    super(resultType);
  }

  public Integer getRemainCoin() {
    return remainCoin;
  }

  public void setRemainCoin(Integer remainCoin) {
    this.remainCoin = remainCoin;
  }

}
