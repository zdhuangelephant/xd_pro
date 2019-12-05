package com.xiaodou.payment.vo.input;

/**
 * Date: 2014/10/22
 * Time: 11:23
 *
 * @author Tian.Dong
 */
public class GetPayStatusPojo {
  private String productLine;
  private String gorderId;
  private String token;

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

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }
}
