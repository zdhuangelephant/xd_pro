package com.xiaodou.resources.response.order;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ProductOrderResponse extends BaseResponse {
  public ProductOrderResponse(ResultType type) {
    super(type);
  }

  public ProductOrderResponse(ProductResType productResType) {
    super(productResType);
  }

  private String gorderId;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

}
