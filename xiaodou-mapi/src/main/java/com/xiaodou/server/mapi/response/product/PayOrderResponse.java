package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.response.product.PayOrderResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月7日
 * @description 支付课程订单响应
 * @version 1.0
 */
public class PayOrderResponse extends BaseResponse {
  public PayOrderResponse(ResultType type) {
    super(type);
  }

  public PayOrderResponse() {
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
