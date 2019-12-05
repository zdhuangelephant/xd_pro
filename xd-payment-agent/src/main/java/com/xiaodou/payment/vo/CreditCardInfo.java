package com.xiaodou.payment.vo;

/**
 * 信用卡信息
 *
 * @author Jiejun.Gao
 */
public class CreditCardInfo {
  private double amt;//交易金额
  private double serviceAmt;//手续费
  private String cardNo;//信用卡号
  private String ecpireDate;//yyyyMMdd
  private String verifyCode;//CVV码
  private String cardHolder;//持卡人
  private Integer idType;//证件类型
  private String idNo;//证件号码
  private int currentcy;//币种，人民币(4601)


  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public String getEcpireDate() {
    return ecpireDate;
  }

  public void setEcpireDate(String ecpireDate) {
    this.ecpireDate = ecpireDate;
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

  public int getCurrentcy() {
    return currentcy;
  }

  public void setCurrentcy(int currentcy) {
    this.currentcy = currentcy;
  }

  public Integer getIdType() {
    return idType;
  }

  public void setIdType(Integer idType) {
    this.idType = idType;
  }

  public double getAmt() {
    return amt;
  }

  public void setAmt(double amt) {
    this.amt = amt;
  }

  public double getServiceAmt() {
    return serviceAmt;
  }

  public void setServiceAmt(double serviceAmt) {
    this.serviceAmt = serviceAmt;
  }


}
