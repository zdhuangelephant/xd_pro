package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Date: 2014/7/18
 * Time: 10:56
 *
 * @author Tian.Dong
 */
public class RefundRequest {
  /**
   * 商户号
   *
   */
  @JSONField(name = "merchant_id")
  protected String merchantId;

  /**
   * 签名方式
   *
   */
  @JSONField(name = "sign_type")
  protected String signType = "MD5";

  /**
   * 签名
   */
  @JSONField(name = "sign")
  protected String sign;

  /**
   * 业务类型
   */
  @JSONField(name = "business_type")
  private Integer businessType;

  /**
   * 请求交易号(token)
   */
  @JSONField(name = "trade_no")
  private Long tradeNo;

  /**
   * 金额，两位小数
   */
  @JSONField(name = "amt")
  private Double amt;


  @JSONField(name = "notify_url")
  private String notifyUrl;

  @JSONField(name = "order_id")
  private String orderId;

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public Long getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(Long tradeNo) {
    this.tradeNo = tradeNo;
  }


  public Double getAmt() {
    return amt;
  }

  public void setAmt(Double amt) {
    this.amt = amt;
  }


  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

}
