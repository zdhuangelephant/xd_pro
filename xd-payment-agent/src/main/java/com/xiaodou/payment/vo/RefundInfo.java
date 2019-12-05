package com.xiaodou.payment.vo;

/**
 * 退款请求的url
 *
 * @author Jiejun.Gao
 */
public class RefundInfo {

  private String merchant_id;
  private String sign_type;
  private String order_id;
  private String sign;
  private Integer business_type;
  private String trade_no;
  private String pre_trade_no;
  private double amt;
  private Integer operation_id;
  private String notify_url;

  public String getMerchant_id() {
    return merchant_id;
  }

  public void setMerchant_id(String merchant_id) {
    this.merchant_id = merchant_id;
  }

  public String getSign_type() {
    return sign_type;
  }

  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public Integer getBusiness_type() {
    return business_type;
  }

  public void setBusiness_type(Integer business_type) {
    this.business_type = business_type;
  }

  public String getTrade_no() {
    return trade_no;
  }

  public void setTrade_no(String trade_no) {
    this.trade_no = trade_no;
  }

  public String getPre_trade_no() {
    return pre_trade_no;
  }

  public void setPre_trade_no(String pre_trade_no) {
    this.pre_trade_no = pre_trade_no;
  }

  public Integer getOperation_id() {
    return operation_id;
  }

  public void setOperation_id(Integer operation_id) {
    this.operation_id = operation_id;
  }

  public String getNotify_url() {
    return notify_url;
  }

  public void setNotify_url(String notify_url) {
    this.notify_url = notify_url;
  }

  public double getAmt() {
    return amt;
  }

  public void setAmt(double amt) {
    this.amt = amt;
  }
}
