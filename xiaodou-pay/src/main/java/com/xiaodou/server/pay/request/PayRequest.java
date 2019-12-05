package com.xiaodou.server.pay.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.request.inner.MixPaymentCa;
import com.xiaodou.server.pay.request.inner.MixPaymentCc;
import com.xiaodou.server.pay.request.inner.MixPaymentDc;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.validator.annotion.ValidateEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayRequest extends BaseRequest {
  /** mixpaymentType 混合模式 */
  @NotEmpty
  @LegalValueList({PayConstant.MIX_PAYMENT_CA, PayConstant.MIX_PAYMENT_DC})
  private Integer mixpaymentType;
  /** tradeNo 请求交易号 */
  @NotEmpty
  private String tradeNo;
  /** orderId 业务订单号 */
  @NotEmpty
  private String orderId;
  /** orderFrom 业务订单来源 */
  @NotEmpty
  @LegalValueList({PayConstant.PAY_FROM_ANDROID, PayConstant.PAY_FROM_CHI_MAIN,
      PayConstant.PAY_FROM_CHI_UNITED, PayConstant.PAY_FROM_ENG_MAIN,
      PayConstant.PAY_FROM_ENG_UNITED, PayConstant.PAY_FROM_H5, PayConstant.PAY_FROM_IPAD,
      PayConstant.PAY_FROM_IPHONE, PayConstant.PAY_FROM_WAP, PayConstant.PAY_FROM_WINP,
      PayConstant.PAY_FROM_UNKNOWN})
  private Integer orderFrom;
  /** payFrom 支付来源 */
  @NotEmpty
  @LegalValueList({PayConstant.PAY_FROM_ANDROID, PayConstant.PAY_FROM_CHI_MAIN,
      PayConstant.PAY_FROM_CHI_UNITED, PayConstant.PAY_FROM_ENG_MAIN,
      PayConstant.PAY_FROM_ENG_UNITED, PayConstant.PAY_FROM_H5, PayConstant.PAY_FROM_IPAD,
      PayConstant.PAY_FROM_IPHONE, PayConstant.PAY_FROM_WAP, PayConstant.PAY_FROM_WINP,
      PayConstant.PAY_FROM_UNKNOWN})
  private Integer payFrom;
  /** totalAmt 支付订单金额 */
  @NotEmpty
  private Double totalAmt;
  /** notifyUrl 异步通知回调URL */
  @NotEmpty
  private String notifyUrl;
  /** cc 信用卡参数 */
  @OrNotEmptyList({@NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CC),
      @NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CA_CC)})
  @ValidateEntity
  private MixPaymentCc cc;
  /** ca 现金账户参数 */
  @OrNotEmptyList({@NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CA),
      @NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CA_CC),
      @NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CA_DC)})
  @ValidateEntity
  private MixPaymentCa ca;
  /** dc 支付网关参数 */
  @OrNotEmptyList({@NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_DC),
      @NotEmpty(field = "mixpaymentType", value = PayConstant.MIX_PAYMENT_CA_DC)})
  @ValidateEntity
  private MixPaymentDc dc;

  public Integer getMixpaymentType() {
    return mixpaymentType;
  }

  public void setMixpaymentType(Integer mixpaymentType) {
    this.mixpaymentType = mixpaymentType;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getOrderFrom() {
    return orderFrom;
  }

  public void setOrderFrom(Integer orderFrom) {
    this.orderFrom = orderFrom;
  }

  public Integer getPayFrom() {
    return payFrom;
  }

  public void setPayFrom(Integer payFrom) {
    this.payFrom = payFrom;
  }

  public Double getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(Double totalAmt) {
    this.totalAmt = totalAmt;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public MixPaymentCc getCc() {
    return cc;
  }

  public void setCc(MixPaymentCc cc) {
    this.cc = cc;
  }

  public MixPaymentCa getCa() {
    return ca;
  }

  public void setCa(MixPaymentCa ca) {
    this.ca = ca;
  }

  public MixPaymentDc getDc() {
    return dc;
  }

  public void setDc(MixPaymentDc dc) {
    this.dc = dc;
  }

}
