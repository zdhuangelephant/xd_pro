package com.xiaodou.resources.response.product;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ReplyPostResponse extends BaseResponse {

  public ReplyPostResponse(ResultType resultType) {
    super(resultType);
  }
  
  public ReplyPostResponse(ProductResType resultType) {
    super(resultType);
  }

}
