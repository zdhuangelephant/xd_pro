package com.xiaodou.resources.response.product;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserProductOrderResponse extends BaseResponse {

  public UserProductOrderResponse(){}
  
  public UserProductOrderResponse(ResultType resultType) {
    super(resultType);
  }

  public UserProductOrderResponse(ProductResType resultType) {
    super(resultType);
  }

}
