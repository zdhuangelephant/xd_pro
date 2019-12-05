package com.xiaodou.payment.vo.request.inner;

import com.alibaba.fastjson.annotation.JSONField;

public class MixPaymentCa {
  
  /** subject 商品名称 */
  @JSONField(name = "subject")
  private String subject;
  
  /** body 商品说明 */
  @JSONField(name = "body")
  
  private String body;
  @JSONField(name = "caAmt")
  private Double amt;

  @JSONField(name = "memberCardNo")
  private String memberCardNo;

  @JSONField(name = "caCurrency")
  private Integer caCurrency;

  @JSONField(name = "productLine")
  private String productLine;

  @JSONField(name = "businessType")
  private Integer businessType;

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

  public Double getAmt() {
    return amt;
  }

  public void setAmt(Double amt) {
    this.amt = amt;
  }

  public Integer getCaCurrency() {
    return caCurrency;
  }

  public void setCaCurrency(Integer caCurrency) {
    this.caCurrency = caCurrency;
  }

  public String getMemberCardNo() {
    return memberCardNo;
  }

  public void setMemberCardNo(String memberCardNo) {
    this.memberCardNo = memberCardNo;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

}
