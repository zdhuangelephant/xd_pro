package com.xiaodou.payment.vo.input;

import com.xiaodou.payment.constant.ContinuePayType;

/**
 * Date: 2014/11/18
 * Time: 15:48
 *
 * @author Tian.Dong
 */
public class ContinuePayNoticePojo {

  private String productLine;

  private String gorderId;

  private long token;
  /** 是否继续支付*/
  private ContinuePayType continuePay;

  private String checkInfo;

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public long getToken() {
    return token;
  }

  public void setToken(long token) {
    this.token = token;
  }

  public ContinuePayType getContinuePay() {
    return continuePay;
  }

  public void setContinuePay(ContinuePayType continuePay) {
    this.continuePay = continuePay;
  }

  public String getCheckInfo() {
    return checkInfo;
  }

  public void setCheckInfo(String checkInfo) {
    this.checkInfo = checkInfo;
  }
}
