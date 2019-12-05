package com.xiaodou.server.pay.request.inner;

import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;


/**
 * @name @see com.xiaodou.server.pay.request.inner.MixPaymentDc.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 网关支付参数
 * @version 1.0
 */
public class MixPaymentDc {
  /** paymentProviderId 交易提供商 */
  @NotEmpty
  @LegalValueList({PayConstant.PAYMENT_PROVIDER_ALIPAY, PayConstant.PAYMENT_PROVIDER_TENPAY,
      PayConstant.PAYMENT_PROVIDER_KUAIQIAN, PayConstant.PAYMENT_PROVIDER_JSYH,
      PayConstant.PAYMENT_PROVIDER_GSYH, PayConstant.PAYMENT_PROVIDER_ZGYH,
      PayConstant.PAYMENT_PROVIDER_NYYH, PayConstant.PAYMENT_PROVIDER_JTYH,
      PayConstant.PAYMENT_PROVIDER_ZSYH, PayConstant.PAYMENT_PROVIDER_UNION,
      PayConstant.PAYMENT_PROVIDER_PAYPAL, PayConstant.PAYMENT_PROVIDER_WXPAY,
      PayConstant.PAYMENT_PROVIDER_APPLEPAY,PayConstant.PAYMETHOD_WX_OFFICIAL_JS})
  private Short paymentProviderId;
  /** dcAmt 交易金额 */
  @NotEmpty
  private Double dcAmt;
  /** subject 商品名称 */
  @NotEmpty
  private String subject;
  /** body 商品说明 */
  @NotEmpty
  private String body;
  /** paymentMethod 支付类型 */
  @NotEmpty
  @LegalValueList({PayConstant.PAYMETHOD_ACCOUNT, PayConstant.PAYMETHOD_CYBERBANK,
      PayConstant.PAYMETHOD_WX_OFFICIAL_JS, PayConstant.PAYMETHOD_WX_OFFICIAL_NATIVE,
      PayConstant.PAYMETHOD_WX_APP, PayConstant.PAYMETHOD_WX_WEB})
  private Short paymentMethod;
  /** externalBankId 外挂银行编码 */
  @NotEmpty
  private Integer externalBankId;
  /** dcCustomerServiceAmt 客户支付的手续费金额 */
  @NotEmpty
  private Double dcCustomerServiceAmt;
  /** cancelUrl 取消支付URL */
  @NotEmpty
  private String cancelUrl;
  /** returnUrl 扣款成功跳转地址 */
  @NotEmpty
  private String returnUrl;
  /** dcCurrency 交易币种 */
  @NotEmpty
  @LegalValueList({PayConstant.PAYMENT_CURRENCY_YUAN})
  private Short dcCurrency;

  @NotEmpty(field = "paymentProviderId", value = PayConstant.PAYMETHOD_WX_OFFICIAL_JS)
  private String openId;

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Short getPaymentProviderId() {
    return paymentProviderId;
  }

  public void setPaymentProviderId(Short paymentProviderId) {
    this.paymentProviderId = paymentProviderId;
  }

  public Double getDcAmt() {
    return dcAmt;
  }

  public void setDcAmt(Double dcAmt) {
    this.dcAmt = dcAmt;
  }

  public Short getDcCurrency() {
    return dcCurrency;
  }

  public void setDcCurrency(Short dcCurrency) {
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

  public Short getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(Short paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Integer getExternalBankId() {
    return externalBankId;
  }

  public void setExternalBankId(Integer externalBankId) {
    this.externalBankId = externalBankId;
  }

  public Double getDcCustomerServiceAmt() {
    return dcCustomerServiceAmt;
  }

  public void setDcCustomerServiceAmt(Double dcCustomerServiceAmt) {
    this.dcCustomerServiceAmt = dcCustomerServiceAmt;
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
