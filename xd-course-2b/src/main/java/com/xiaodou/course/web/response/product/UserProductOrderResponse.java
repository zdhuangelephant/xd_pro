package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class UserProductOrderResponse extends BaseResponse {

  public UserProductOrderResponse(ResultType resultType) {
    super(resultType);
  }

  public UserProductOrderResponse(ProductResType resultType) {
    super(resultType);
  }

  public UserProductOrderResponse(UcenterResType resultType) {
    super(resultType);
  }
}
