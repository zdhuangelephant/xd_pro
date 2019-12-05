package com.xiaodou.payment.vo.request.inner;

import com.alibaba.fastjson.annotation.JSONField;

public class MixPaymentCc {

  @JSONField(name = "ccAmt")
  private Double amt;

  @JSONField(name = "ccCustomerServiceAmt")
  private Double customerServiceAmt;

  @JSONField(name = "creditCardNo")
  private String creditCardNo;

  @JSONField(name = "expireDate")
  private String expireDate;

  @JSONField(name = "verifyCode")
  private String verifyCode;

  @JSONField(name = "cardHolder")
  private String cardHolder;

  @JSONField(name = "idNo")
  private String idNo;

  @JSONField(name = "idType")
  private Integer idType;

  @JSONField(name = "ccCurrency")
  private Integer ccCurrency;

  public Double getAmt() {
    return amt;
  }

  public void setAmt(Double amt) {
    this.amt = amt;
  }

  public Double getCustomerServiceAmt() {
    return customerServiceAmt;
  }

  public void setCustomerServiceAmt(Double customerServiceAmt) {
    this.customerServiceAmt = customerServiceAmt;
  }

  public String getCreditCardNo() {
    return creditCardNo;
  }

  public void setCreditCardNo(String creditCardNo) {
    this.creditCardNo = creditCardNo;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public String getCardHolder() {
    return cardHolder;
  }

  public void setCardHolder(String cardHolder) {
    this.cardHolder = cardHolder;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public Integer getIdType() {
    return idType;
  }

  public void setIdType(Integer idType) {
    this.idType = idType;
  }

  public Integer getCcCurrency() {
    return ccCurrency;
  }

  public void setCcCurrency(Integer ccCurrency) {
    this.ccCurrency = ccCurrency;
  }

}
