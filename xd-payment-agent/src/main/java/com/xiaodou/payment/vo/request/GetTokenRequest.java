package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Date: 2014/7/18 Time: 15:40
 * 
 * @author Tian.Dong
 */
public class GetTokenRequest {
  @JSONField(name = "businessType")
  private int businessType;
  @JSONField(name = "merchantId")
  private String merchantId;
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
  @JSONField(name = "outTradeNo")
  private String outTradeNo;

  public int getBusinessType() {
    return businessType;
  }

  public void setBusinessType(int businessType) {
    this.businessType = businessType;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
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
