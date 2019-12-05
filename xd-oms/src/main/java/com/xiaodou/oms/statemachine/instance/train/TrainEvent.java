package com.xiaodou.oms.statemachine.instance.train;

/**
 *
 * @Title:TrainEvent.java
 *
 * @Description:火车票业务的事件
 *
 * @author  zhaoyang
 * @date    June 16, 2014 3:24:35 PM
 * @version V1.0
 */
public enum TrainEvent {
  EntryQueueFailure("EntryQueueFailure","进入占座队列失败"),
  HoldingSeatFailure("HoldingSeatFailure","占座失败"),
  HoldingSeatSuccess("HoldingSeatSuccess","占座成功"),
  EntryQueueSuccess("EntryQueueSuccess","进入占座队列成功"),
  PaySuccess("PaySuccess","支付成功"),
  PayFailure("payFailure","支付失败"),
  PayTimeout("payTimeout", "支付超时"),
  RefundSuccess("RefundSuccess", "退款成功"),
  RefundFailure("RefundFailure", "退款失败"),
  CancelByUser("cancelByUser", "用户取消"),
  CancelByFraud("CancelByFraud","风控拒绝"),
  DeliveringSuccess("deliveringSuccess", "配单成功"),
  OrderReturn("orderReturn", "订单返库"),
  DeliveredSuccess("deliveredSuccess", "出票成功"),
  NeedSencondPay("needSencondPay", "待退差价"),
  DeliveredFailure("deliveredFailure", "出票失败"),
  DeliveredTimeout("deliveredTimeout", "出票超时"),
  SecondPaySuccess("secondPaySuccess", "退差价成功"),
  TicketRefundRequest("ticketRefundRequest", "退票请求"),
  TicketRefundSuccess("ticketRefundSuccess", "退票成功"),
  TicketRefundFailure("ticketRefundFailure", "退票失败"),
  RefundRequest("refundRequest", "退款请求");

  private String name;

  private String desc;

  private TrainEvent(String name, String desc) {
    this.name = name;
    this.desc = desc;
  }

  public String getName() {
    return this.name;
  }

  public String getDesc() {
    return this.desc;
  }
}
