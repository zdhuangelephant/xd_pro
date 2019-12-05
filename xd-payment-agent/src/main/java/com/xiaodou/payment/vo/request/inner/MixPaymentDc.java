package com.xiaodou.payment.vo.request.inner;

import com.alibaba.fastjson.annotation.JSONField;

public class MixPaymentDc {


  @JSONField(name = "paymentProviderId")
  private Integer paymentProvider;

  @JSONField(name = "dcAmt")
  private Double amt;

  @JSONField(name = "dcCurrency")
  private Integer dcCurrency;

  @JSONField(name = "subject")
  private String subject;

  @JSONField(name = "body")
  private String body;

  @JSONField(name = "paymentMethod")
  private Integer paymentMethod;

  @JSONField(name = "externalBankId")
  private Integer externalBankId;

  @JSONField(name = "dcCustomerServiceAmt")
  private Double customerServiceAmt;

  @JSONField(name = "cancelUrl")
  private String cancelUrl;

  @JSONField(name = "returnUrl")
  private String returnUrl;

  @JSONField(name = "openId")
  private String openId;

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Integer getPaymentProvider() {
    return paymentProvider;
  }

  public void setPaymentProvider(Integer paymentProvider) {
    this.paymentProvider = paymentProvider;
  }

  public Double getAmt() {
    return amt;
  }

  public void setAmt(Double amt) {
    this.amt = amt;
  }

  public Integer getDcCurrency() {
    return dcCurrency;
  }

  public void setDcCurrency(Integer dcCurrency) {
    this.dcCurrency = dcCurrency;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Integer getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(Integer paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Integer getExternalBankId() {
    return externalBankId;
  }

  public void setExternalBankId(Integer externalBankId) {
    this.externalBankId = externalBankId;
  }

  public Double getCustomerServiceAmt() {
    return customerServiceAmt;
  }

  public void setCustomerServiceAmt(Double customerServiceAmt) {
    this.customerServiceAmt = customerServiceAmt;
  }

  public String getCancelUrl() {
    return cancelUrl;
  }

  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }
}
