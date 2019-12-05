package com.xiaodou.server.mapi.response.selftaught;

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
  /**
   * payStatus 扩展字段,如果是实时进行支付,则返回支付的真正结果(未来快捷支付可能会使用),默认是"2",2:已接收支付请求,等待处理;0:支付成功;1:支付失败
   */
  private String payStatus;// 支付状态（0:支付成功1:支付失败2:正在支付）
  /**
   * payInfo 支付结果信息，支付成功时为空
   */
  private String payInfo;

  public PayResponse() {

  }

  public PayResponse(ResultType type) {
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
}
