package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserCoinRemainResponse extends BaseResponse {
  /* 购买课程后用户预计剩余金币 */
  private Integer remainCoin;

  public UserCoinRemainResponse(ResultType resultType) {
    super(resultType);
  }

  public UserCoinRemainResponse(ProductResType resultType) {
    super(resultType);
  }

  public Integer getRemainCoin() {
    return remainCoin;
  }

  public void setRemainCoin(Integer remainCoin) {
    this.remainCoin = remainCoin;
  }

}
