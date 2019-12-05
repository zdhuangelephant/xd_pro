package com.xiaodou.payment.vo;

/**
 * 订单信息
 *
 * @author Jiejun.Gao
 */
public class OrderInfo {
  private String orderId;
  private String notifyUrl;
  private String orderFrom;
  private Integer payFrom;
  private double totalAmt;
  private Integer paymentProductId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }


  public Integer getPaymentProductId() {
    return paymentProductId;
  }

  public void setPaymentProductId(Integer paymentProductId) {
    this.paymentProductId = paymentProductId;
  }

  public String getOrderFrom() {
    return orderFrom;
  }

  public void setOrderFrom(String orderFrom) {
    this.orderFrom = orderFrom;
  }

  public Integer getPayFrom() {
    return payFrom;
  }

  public void setPayFrom(Integer payFrom) {
    this.payFrom = payFrom;
  }

  public double getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(double totalAmt) {
    this.totalAmt = totalAmt;
  }
}
