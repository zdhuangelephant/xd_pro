package com.xiaodou.server.pay.request.inner;

import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;


/**
 * @name @see com.xiaodou.server.pay.request.inner.MixPaymentCc.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 信用卡参数
 * @version 1.0
 */
public class MixPaymentCc {

  /** ccAmt 交易金额 */
  @NotEmpty
  private Double ccAmt;
  /** ccCustomerServiceAmt 客户支付的手续费金额 */
  @NotEmpty
  private Double ccCustomerServiceAmt;
  /** creditCardNo 信用卡号 */
  @NotEmpty
  private String creditCardNo;
  /** expireDate 有效期 */
  @NotEmpty
  private String expireDate;
  /** verifyCode 信用卡验证码 */
  @NotEmpty
  private String verifyCode;
  /** cardHolder 信用卡持卡人 */
  @NotEmpty
  private String cardHolder;
  /** idNo 证件号码 */
  @NotEmpty
  private String idNo;
  /** idType 持卡人证件类型 */
  @NotEmpty
  @LegalValueList({PayConstant.ID_TYPE_CARD, PayConstant.ID_TYPE_PASSPORT,
      PayConstant.ID_TYPE_OTHER, PayConstant.ID_TYPE_ARMY, PayConstant.ID_TYPE_POLICE,
      PayConstant.ID_TYPE_HOME})
  private Short idType;
  /** ccCurrency 交易币种 */
  @NotEmpty
  @LegalValueList({PayConstant.PAYMENT_CURRENCY_YUAN})
  private Short ccCurrency;

  public Double getCcAmt() {
    return ccAmt;
  }

  public void setCcAmt(Double ccAmt) {
    this.ccAmt = ccAmt;
  }

  public Double getCcCustomerServiceAmt() {
    return ccCustomerServiceAmt;
  }

  public void setCcCustomerServiceAmt(Double ccCustomerServiceAmt) {
    this.ccCustomerServiceAmt = ccCustomerServiceAmt;
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

  public Short getIdType() {
    return idType;
  }

  public void setIdType(Short idType) {
    this.idType = idType;
  }

  public Short getCcCurrency() {
    return ccCurrency;
  }

  public void setCcCurrency(Short ccCurrency) {
    this.ccCurrency = ccCurrency;
  }

}
