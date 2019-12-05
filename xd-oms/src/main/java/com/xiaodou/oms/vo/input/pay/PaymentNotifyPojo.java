package com.xiaodou.oms.vo.input.pay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * payment回调参数
 * 
 * Date: 2014/7/1 Time: 13:55
 * 
 * @author Tian.Dong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentNotifyPojo extends BaseValidatorPojo {
  /**
   * 商户号
   */
  private String merchantId;
  /**
   * 签名类型
   */
  private String signType;
  /**
   * 签名
   */
  private String sign;
  /**
   * 即token
   */
  @NotEmpty
  private String tradeNo;
  /**
   * 支付时间
   */
  private String payTime;
  /**
   * 支付状态
   */
  @NotEmpty
  private Integer resultStatus;
  /**
   * 错误信息
   */
  private String errorInfo;

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
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

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  public Integer getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(Integer resultStatus) {
    this.resultStatus = resultStatus;
  }

  public String getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(String errorInfo) {
    this.errorInfo = errorInfo;
  }

  @Override
  public String toString() {
    return String
        .format(
            "PaymentNotifyPojo{merchantId='%s' , signType='%s', sign='%s',tradeNo='%s', payTime='%s' , resultStatus ='%s', errorInfo='%s'}",
            merchantId, signType, sign, tradeNo, payTime, resultStatus, errorInfo);
  }
}
