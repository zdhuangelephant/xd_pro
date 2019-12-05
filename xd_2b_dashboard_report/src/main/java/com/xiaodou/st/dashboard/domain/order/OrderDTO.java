package com.xiaodou.st.dashboard.domain.order;

public class OrderDTO {

  private Short status;
  private Long orderNumber;
  private String orderTime;
  private String payTime;

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }
  
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = Long.parseLong(orderNumber);
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(String orderTime) {
    this.orderTime = orderTime;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }
}
