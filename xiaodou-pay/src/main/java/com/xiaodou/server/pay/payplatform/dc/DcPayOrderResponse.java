package com.xiaodou.server.pay.payplatform.dc;

import com.xiaodou.server.pay.payplatform.IPayOrderResponse;
import com.xiaodou.summer.vo.out.ResultInfo;

/**
 * @name @see com.xiaodou.server.pay.thirdpayplaform.PayOrderResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月29日
 * @description 三方平台支付下单结果
 * @version 1.0
 */
public class DcPayOrderResponse extends ResultInfo implements IPayOrderResponse {
  /** thirdPlatformId 三方支付平台交易号 */
  private String thirdPlatformTradeNo;
  /** userThirdPlatformId 商户三方平台ID */
  private String thirdPlatformId;
  /** totalAmount 总金额 */
  private Double totalAmount;
  /** payUrl 网关支付时支付url */
  private String payUrl;
  /** payStatus 支付状态 */
  private Short payStatus;
  /** payInfo 支付结果信息 */
  private String payInfo;


  public String getThirdPlatformTradeNo() {
    return thirdPlatformTradeNo;
  }

  public void setThirdPlatformTradeNo(String thirdPlatformTradeNo) {
    this.thirdPlatformTradeNo = thirdPlatformTradeNo;
  }

  public String getThirdPlatformId() {
    return thirdPlatformId;
  }

  public void setThirdPlatformId(String thirdPlatformId) {
    this.thirdPlatformId = thirdPlatformId;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getPayUrl() {
    return payUrl;
  }

  public void setPayUrl(String payUrl) {
    this.payUrl = payUrl;
  }

  public Short getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(Short payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }

  @Override
  public boolean isPaySuccess() {
    // TODO Auto-generated method stub
    return false;
  }

}
