package com.xiaodou.payment.vo.input;

/**
 * Date: 2014/7/23
 * Time: 14:58
 *
 * @author Tian.Dong
 */
public class GetPayTypePojo {
  private String productLine;
  private String token;
  private String gorderId;

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
