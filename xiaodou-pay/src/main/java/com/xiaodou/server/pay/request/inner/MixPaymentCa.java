package com.xiaodou.server.pay.request.inner;

import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.server.pay.request.inner.MixPaymentCa.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 现金账户参数
 * @version 1.0
 */
public class MixPaymentCa {
  /** subject 商品名称 */
  @NotEmpty
  private String subject;
  /** body 商品说明 */
  @NotEmpty
  private String body;
  /** caAmt 交易金额 */
  @NotEmpty
  private Double caAmt;
  /** memberCardNo 会员卡号 */
  @NotEmpty
  private String memberCardNo;
  /** productLine 所属产品线 */
  @NotEmpty
  private String productLine;
  /** businessType 业务类型 */
  @NotEmpty
  private Integer businessType;
  /** caCurrency 币种类型 */
  @NotEmpty
  @LegalValueList({PayConstant.PAYMENT_CURRENCY_YUAN, PayConstant.PAYMENT_CURRENCY_IOS,
      PayConstant.PAYMENT_CURRENCY_ANDROID})
  private Short caCurrency;

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

  public Double getCaAmt() {
    return caAmt;
  }

  public void setCaAmt(Double caAmt) {
    this.caAmt = caAmt;
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

  public Short getCaCurrency() {
    return caCurrency;
  }

  public void setCaCurrency(Short caCurrency) {
    this.caCurrency = caCurrency;
  }

}
