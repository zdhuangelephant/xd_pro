package com.xiaodou.course.web.response.order;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class PayOrderResponse extends BaseResponse {
  public PayOrderResponse(ResultType type) {
    super(type);
  }

  public PayOrderResponse(ProductResType productResType) {
    super(productResType);
  }
  /** payStatus 支付状态 */
  private String payStatus;
  /** payInfo 支付结果信息 */
  private String payInfo;
  public String getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }
}
