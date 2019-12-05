package com.xiaodou.oms.statemachine.instance.trainSelfPay;

/**
 * Date: 2015/1/7
 * Time: 11:03
 *
 * @author Tian.Dong
 */
public enum TrainSelfPayEvent {

  EnterQueueSuccess("EnterQueueSuccess", "进入队列成功"),
  EnterQueueFailure("EnterQueueFailure", "进入队列失败"),
  HoldingSeatSuccess("HoldingSeatSuccess", "占座成功"),
  HoldingSeatFailure("HoldingSeatFailure", "占座失败"),
  PaySuccess("PaySuccess", "支付成功"),
  PayFailure("PayFailure", "支付失败"),
  CancelByUser("CancelByUser", "用户取消"),
  CancelByFraud("CancelByFraud","风控拒绝"),
  Pay12306Timeout("Pay12306Timeout", "12306超时"),
  EnterDeliveringQueue("EnterDeliveringQueue", "进入12306支付队列"),
  Pay12306Success("Pay12306Success", "12306支付成功"),
  Pay12306Failure("Pay12306Failure", "12306支付失败"),
  DeliveredFailure("DeliveredFailure", "出票失败"),
  TicketRefundSuccess("TicketRefundSuccess", "退票成功"),
  TicketRefundFailure("TicketRefundFailure", "退票失败"),
  RefundRequest("RefundRequest", "退款请求"),
  RefundSuccess("RefundSuccess", "退款成功"),
  RefundFailure("RefundFailure", "退款失败"),
  DeliveredSuccessBySystem("DeliveredSuccessBySystem","系统出票成功"),
  DeliveredSuccessByMan("DeliveredSuccessByMan","人工出票成功");

  private String name;

  private String desc;

  private TrainSelfPayEvent(String name, String desc) {
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
