package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.response.product.ProductOrderResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月7日
 * @description 创建课程订单响应
 * @version 1.0
 */
public class ProductOrderResponse extends BaseResponse {
  public ProductOrderResponse(ResultType type) {
    super(type);
  }

  public ProductOrderResponse() {}

  private String gorderId;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

}
