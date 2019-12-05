package com.xiaodou.server.pay.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.server.pay.model.PayRecord.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 支付记录模型
 * @version 1.0
 */
public class PayRecord {
  /** id 主键ID */
  @Column(isMajor = true, sortBy = true)
  private String id;
  /** tradeNo 交易流水号 */
  @Column(canUpdate = false, sortBy = false)
  private String tradeNo;
  /** preTradeNo 前置交易流水号 */
  @Column(canUpdate = false, sortBy = false)
  private String preTradeNo;
  /** payType 支付类型 */
  @Column(canUpdate = false)
  private Short payType;
  /** merchantId 商户ID */
  @Column(canUpdate = false)
  private String merchantId;
  /** businessType 业务类型 */
  @Column(canUpdate = false)
  private Integer businessType;
  /** outTradeNo 外部订单号 */
  @Column(canUpdate = false, sortBy = false)
  private String outTradeNo;
  /** orderFrom 订单来源 */
  @Column(canUpdate = false)
  private Integer orderFrom;
  /** payFrom 支付来源 */
  @Column(canUpdate = false)
  private Integer payFrom;
  /** orderAmt 订单金额 */
  @Column(canUpdate = false, sortBy = false)
  private Double orderAmt;
  /** originalPayAmt 应付金额 */
  @Column(canUpdate = false, sortBy = false)
  private Double originalPayAmt;
  /** realPayAmt 实付金额 */
  private Double realPayAmt;
  /** payStatus 支付状态 */
  private Short payStatus;
  /** payResult 支付结果信息 */
  private String payResult;
  /** createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;
  /** updateTime 更新时间 */
  private Timestamp updateTime;
  /** finishTime 完成时间 */
  private Timestamp finishTime;
  /** businessCallbackUrl 支付成功回调业务系统地址 */
  @Column(canUpdate = false, sortBy = false)
  private String businessCallbackUrl;
  /** ccAmt cc支付金额 */
  private Double ccAmt;
  /** ccCustomerServiceAmt cc客户手续费金额 */
  private Double ccCustomerServiceAmt;
  /** ccNo cc信用卡号 */
  private String ccNo;
  /** ccExpireDate cc信用卡有效期 */
  private String ccExpireDate;
  /** ccVerifyCode cc信用卡验证码 */
  private String ccVerifyCode;
  /** ccCardHolder cc信用卡持卡人 */
  private String ccCardHolder;
  /** ccIdType cc持卡人证件类型 */
  private Short ccIdType;
  /** ccIdNo cc证件号码 */
  private String ccIdNo;
  /** ccCurrency cc交易币种 */
  private Short ccCurrency;
  /** ccCurrency ca交易金额 */
  private Double caAmt;
  /** caProductLine ca产品线 */
  private String caProductLine;
  /** caMemcardNo ca会员卡号 */
  private String caMemcardNo;
  /** caCurrency ca交易币种 */
  private Short caCurrency;
  /** dcPaymentProvider dc交易提供商 */
  private Short dcPaymentProvider;
  /** dcAmt dc交易金额 */
  private Double dcAmt;
  /** dcCustomerServiceAmt dc客户手续费金额 */
  private Double dcCustomerServiceAmt;
  /** dcCurrency dc交易币种 */
  private Short dcCurrency;
  /** dcTradeNo dc三方交易号 */
  private String dcTradeNo;
  /** dcPaymentMethod dc支付类型 */
  private Short dcPaymentMethod;
  /** dcExternalbankId dc外挂银行编码 */
  private Integer dcExternalbankId;
  /** dcSubject dc商品名称 */
  private String dcSubject;
  /** dcBody dc商品说明 */
  private String dcBody;
  /** dcReturnUrl dc扣款成功跳转地址 */
  private String dcReturnUrl;
  /** dcCancelUrl dc取消支付url */
  private String dcCancelUrl;
  /** dcPayUrl dc支付url */
  private String dcPayUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getPreTradeNo() {
    return preTradeNo;
  }

  public void setPreTradeNo(String preTradeNo) {
    this.preTradeNo = preTradeNo;
  }

  public Short getPayType() {
    return payType;
  }

  public void setPayType(Short payType) {
    this.payType = payType;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
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

  public Double getOrderAmt() {
    return orderAmt;
  }

  public void setOrderAmt(Double orderAmt) {
    this.orderAmt = orderAmt;
  }

  public Double getOriginalPayAmt() {
    return originalPayAmt;
  }

  public void setOriginalPayAmt(Double originalPayAmt) {
    this.originalPayAmt = originalPayAmt;
  }

  public Double getRealPayAmt() {
    return realPayAmt;
  }

  public void setRealPayAmt(Double realPayAmt) {
    this.realPayAmt = realPayAmt;
  }

  public void addRealPayAmt(Double realPayAmt) {
    if (null == realPayAmt) return;
    if (null == this.realPayAmt)
      setRealPayAmt(realPayAmt);
    else if (null != this.realPayAmt) this.realPayAmt += realPayAmt;
  }

  public Short getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(Short payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayResult() {
    return payResult;
  }

  public void setPayResult(String payResult) {
    this.payResult = payResult;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public Timestamp getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Timestamp finishTime) {
    this.finishTime = finishTime;
  }

  public String getBusinessCallbackUrl() {
    return businessCallbackUrl;
  }

  public void setBusinessCallbackUrl(String businessCallbackUrl) {
    this.businessCallbackUrl = businessCallbackUrl;
  }

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

  public String getCcNo() {
    return ccNo;
  }

  public void setCcNo(String ccNo) {
    this.ccNo = ccNo;
  }

  public String getCcExpireDate() {
    return ccExpireDate;
  }

  public void setCcExpireDate(String ccExpireDate) {
    this.ccExpireDate = ccExpireDate;
  }

  public String getCcVerifyCode() {
    return ccVerifyCode;
  }

  public void setCcVerifyCode(String ccVerifyCode) {
    this.ccVerifyCode = ccVerifyCode;
  }

  public String getCcCardHolder() {
    return ccCardHolder;
  }

  public void setCcCardHolder(String ccCardHolder) {
    this.ccCardHolder = ccCardHolder;
  }

  public Short getCcIdType() {
    return ccIdType;
  }

  public void setCcIdType(Short ccIdType) {
    this.ccIdType = ccIdType;
  }

  public String getCcIdNo() {
    return ccIdNo;
  }

  public void setCcIdNo(String ccIdNo) {
    this.ccIdNo = ccIdNo;
  }

  public Short getCcCurrency() {
    return ccCurrency;
  }

  public void setCcCurrency(Short ccCurrency) {
    this.ccCurrency = ccCurrency;
  }

  public Double getCaAmt() {
    return caAmt;
  }

  public void setCaAmt(Double caAmt) {
    this.caAmt = caAmt;
  }

  public String getCaMemcardNo() {
    return caMemcardNo;
  }

  public void setCaMemcardNo(String caMemcardNo) {
    this.caMemcardNo = caMemcardNo;
  }

  public String getCaProductLine() {
    return caProductLine;
  }

  public void setCaProductLine(String caProductLine) {
    this.caProductLine = caProductLine;
  }

  public Short getCaCurrency() {
    return caCurrency;
  }

  public void setCaCurrency(Short caCurrency) {
    this.caCurrency = caCurrency;
  }

  public Short getDcPaymentProvider() {
    return dcPaymentProvider;
  }

  public void setDcPaymentProvider(Short dcPaymentProvider) {
    this.dcPaymentProvider = dcPaymentProvider;
  }

  public Double getDcAmt() {
    return dcAmt;
  }

  public void setDcAmt(Double dcAmt) {
    this.dcAmt = dcAmt;
  }

  public Double getDcCustomerServiceAmt() {
    return dcCustomerServiceAmt;
  }

  public void setDcCustomerServiceAmt(Double dcCustomerServiceAmt) {
    this.dcCustomerServiceAmt = dcCustomerServiceAmt;
  }

  public Short getDcCurrency() {
    return dcCurrency;
  }

  public void setDcCurrency(Short dcCurrency) {
    this.dcCurrency = dcCurrency;
  }

  public String getDcTradeNo() {
    return dcTradeNo;
  }

  public void setDcTradeNo(String dcTradeNo) {
    this.dcTradeNo = dcTradeNo;
  }

  public Short getDcPaymentMethod() {
    return dcPaymentMethod;
  }

  public void setDcPaymentMethod(Short dcPaymentMethod) {
    this.dcPaymentMethod = dcPaymentMethod;
  }

  public Integer getDcExternalbankId() {
    return dcExternalbankId;
  }

  public void setDcExternalbankId(Integer dcExternalbankId) {
    this.dcExternalbankId = dcExternalbankId;
  }

  public String getDcSubject() {
    return dcSubject;
  }

  public void setDcSubject(String dcSubject) {
    this.dcSubject = dcSubject;
  }

  public String getDcBody() {
    return dcBody;
  }

  public void setDcBody(String dcBody) {
    this.dcBody = dcBody;
  }

  public String getDcReturnUrl() {
    return dcReturnUrl;
  }

  public void setDcReturnUrl(String dcReturnUrl) {
    this.dcReturnUrl = dcReturnUrl;
  }

  public String getDcCancelUrl() {
    return dcCancelUrl;
  }

  public void setDcCancelUrl(String dcCancelUrl) {
    this.dcCancelUrl = dcCancelUrl;
  }

  public String getDcPayUrl() {
    return dcPayUrl;
  }

  public void setDcPayUrl(String dcPayUrl) {
    this.dcPayUrl = dcPayUrl;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(PayRecord.class, "xd_payment_pay_record",
        "D:\\MyWorkSpace\\MyEclipse8.5\\xiaodou-pay\\src\\main\\resources\\conf\\mybatis\\pay")
        .buildXml();
  }
}
