package com.xiaodou.server.pay.response;

import com.xiaodou.server.pay.payplatform.ca.CaPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.pay.response.PayResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 支付接口出参
 * @version 1.0
 */
public class PayResponse extends BaseResponse {

  /** payUrl 网关支付时支付url */
  private String payUrl;
  /**payStatus 支付状态（0:正在支付1:支付成功2:支付失败）*/
  private String payStatus;
  /**payInfo 支付结果信息，支付成功时为空*/
  private String payInfo;

  public PayResponse(ResultType type) {
    super(type);
  }

  public PayResponse(PaymentResType type) {
    super(type);
  }

  public String getPayUrl() {
    return payUrl;
  }

  public void setPayUrl(String payUrl) {
    this.payUrl = payUrl;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }

  public String getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public void setCaInfo(CaPayOrderResponse orderResponse) {
    if (null != orderResponse.getPayStatus()) {
      this.payStatus = orderResponse.getPayStatus().toString();
      this.payInfo = orderResponse.getPayInfo();
    }
  }

  public void setDcInfo(DcPayOrderResponse orderResponse) {
    if (null == orderResponse || null == orderResponse.getPayStatus()) {
      throw new RuntimeException();
    }
    this.payStatus = orderResponse.getPayStatus().toString();
    this.payInfo = orderResponse.getPayInfo();
    this.payUrl = orderResponse.getPayUrl();
  }

}
