package com.xiaodou.payment.vo.input;

/**
 * 退款对外提供的对象
 *
 * Date: 2014/7/21
 * Time: 17:06
 *
 * @author Tian.Dong
 */
public class RefundPojo {
  /**
   * 大订单号
   */
  private String gorderId;
  /**
   * token
   */
  private String token;
  /**
   * 退款金额
   */
  private double amt;
  /**
   * 回调地址
   */
  private String notifyUrl;
  /**
   * 产品线
   */
  private String productLine;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public double getAmt() {
    return amt;
  }

  public void setAmt(double amt) {
    this.amt = amt;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }
}
