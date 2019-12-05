package com.xiaodou.oms.agent.model.statemachine;


import java.util.List;

import com.xiaodou.oms.agent.model.Gorder;
import com.xiaodou.oms.agent.model.Order;
import com.xiaodou.oms.agent.model.OrderItem;

public class StateMachineFireCoreParams {

  /** 大订单ID */
  private String gorderId;

  /** 订单ID */
  private String orderId;

  /** 供应商订单ID */
  private String merchantOrderId;

  /** ip */
  private String ip;

  /** 产品线 */
  private String productLine;

  /** 事件名字 */
  private String event;

  /** 支付相关的信息 */
  private PayParam payParam;

  /** gorder需要更新的信息 */
  private Gorder gorderUpdateInfo;

  /** order需要更新的信息 */
  private Order orderUpdateInfo;

  /** orderitem需要更新的信息 */
  private List<OrderItem> orderItemUpdateInfo;

  private Boolean isNeedCloseOrderItem;

  public Gorder getGorderUpdateInfo() {
    return gorderUpdateInfo;
  }

  public void setGorderUpdateInfo(Gorder gorderUpdateInfo) {
    this.gorderUpdateInfo = gorderUpdateInfo;
  }

  public Order getOrderUpdateInfo() {
    return orderUpdateInfo;
  }

  public void setOrderUpdateInfo(Order orderUpdateInfo) {
    this.orderUpdateInfo = orderUpdateInfo;
  }

  public List<OrderItem> getOrderItemUpdateInfo() {
    return orderItemUpdateInfo;
  }

  public void setOrderItemUpdateInfo(List<OrderItem> orderItemUpdateInfo) {
    this.orderItemUpdateInfo = orderItemUpdateInfo;
  }

  public PayParam getPayParam() {
    return payParam;
  }

  public void setPayParam(PayParam payParam) {
    this.payParam = payParam;
  }

  public String getMerchantOrderId() {
    return merchantOrderId;
  }

  public void setMerchantOrderId(String merchantOrderId) {
    this.merchantOrderId = merchantOrderId;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public Boolean getIsNeedCloseOrderItem() {
    return isNeedCloseOrderItem;
  }

  public void setIsNeedCloseOrderItem(Boolean isNeedCloseOrderItem) {
    this.isNeedCloseOrderItem = isNeedCloseOrderItem;
  }

}
