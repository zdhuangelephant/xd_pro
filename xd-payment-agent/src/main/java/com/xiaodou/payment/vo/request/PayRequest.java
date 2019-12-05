package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.payment.vo.request.inner.MixPaymentCa;
import com.xiaodou.payment.vo.request.inner.MixPaymentCc;
import com.xiaodou.payment.vo.request.inner.MixPaymentDc;

/**
 * Date: 2014/7/18
 * Time: 10:56
 *
 * @author Tian.Dong
 */
public class PayRequest {
  /**
   * 商户号
   *
   */
  @JSONField(name = "merchantId")
  protected String merchantId;

  /**
   * 签名方式
   *
   */
  @JSONField(name = "signType")
  protected String signType = "MD5";

  /**
   * 签名
   */
  @JSONField(name = "sign")
  protected String sign;

  @JSONField(name = "businessType")
  private Integer businessType;

  @JSONField(name = "mixpaymentType")
  private Integer mixPaymentType;

  @JSONField(name = "tradeNo")
  private Long tradeNo;

  @JSONField(name = "notifyUrl")
  private String notifyUrl;

  @JSONField(name = "orderId")
  private String orderId;

  @JSONField(name = "totalAmt")
  private Double totalAmt;

  @JSONField(name = "orderFrom")
  private String orderFrom;

  @JSONField(name = "payFrom")
  private Integer payFrom;

  @JSONField(name = "cc")
  private MixPaymentCc mixPaymentCc;

  @JSONField(name = "ca")
  private MixPaymentCa mixPaymentCa;

  @JSONField(name = "dc")
  private MixPaymentDc mixPaymentDc;


  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public Integer getMixPaymentType() {
    return mixPaymentType;
  }

  public void setMixPaymentType(Integer mixPaymentType) {
    this.mixPaymentType = mixPaymentType;
  }

  public Long getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(Long tradeNo) {
    this.tradeNo = tradeNo;
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

  public Double getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(Double totalAmt) {
    this.totalAmt = totalAmt;
  }

  public String getOrderFrom() {
    return orderFrom;
  }

  public void setOrderFrom(String orderFrom) {
    this.orderFrom = orderFrom;
  }

  public Integer getPayFrom() {
    return payFrom;
  }

  public void setPayFrom(Integer payFrom) {
    this.payFrom = payFrom;
  }

  public MixPaymentCc getMixPaymentCc() {
    return mixPaymentCc;
  }

  public void setMixPaymentCc(MixPaymentCc mixPaymentCc) {
    this.mixPaymentCc = mixPaymentCc;
  }

  public MixPaymentCa getMixPaymentCa() {
    return mixPaymentCa;
  }

  public void setMixPaymentCa(MixPaymentCa mixPaymentCa) {
    this.mixPaymentCa = mixPaymentCa;
  }

  public MixPaymentDc getMixPaymentDc() {
    return mixPaymentDc;
  }

  public void setMixPaymentDc(MixPaymentDc mixPaymentDc) {
    this.mixPaymentDc = mixPaymentDc;
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
