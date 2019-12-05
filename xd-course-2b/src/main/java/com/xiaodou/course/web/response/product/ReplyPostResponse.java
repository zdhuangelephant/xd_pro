package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ReplyPostResponse extends BaseResponse {

  public ReplyPostResponse(ResultType resultType) {
    super(resultType);
  }
  
  public ReplyPostResponse(ProductResType resultType) {
    super(resultType);
  }

}
