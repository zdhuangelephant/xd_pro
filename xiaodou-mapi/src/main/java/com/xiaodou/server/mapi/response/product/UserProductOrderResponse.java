package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.enums.ProduceResType;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserProductOrderResponse extends BaseResponse {

  public UserProductOrderResponse() {}

  public UserProductOrderResponse(ResultType resultType) {
    super(resultType);
  }

  public UserProductOrderResponse(ProduceResType resultType) {
    super(resultType);
  }
}
