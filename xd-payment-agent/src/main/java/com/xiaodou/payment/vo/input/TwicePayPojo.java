package com.xiaodou.payment.vo.input;

/**
 * Date: 2014/7/24
 * Time: 13:25
 *
 * @author Tian.Dong
 */
public class TwicePayPojo {
  private String productLine;
  private String token;
  private String preToken;

  private double amount;
  private double serviceAmount;

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getPreToken() {
    return preToken;
  }

  public void setPreToken(String preToken) {
    this.preToken = preToken;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getServiceAmount() {
    return serviceAmount;
  }

  public void setServiceAmount(double serviceAmount) {
    this.serviceAmount = serviceAmount;
  }
}
