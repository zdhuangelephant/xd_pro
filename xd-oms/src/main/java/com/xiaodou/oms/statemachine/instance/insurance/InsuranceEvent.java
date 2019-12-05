package com.xiaodou.oms.statemachine.instance.insurance;

/**
 * 
 * @Title:InsuranceEvent.java
 * 
 * @Description:保险业务的事件
 * 
 * @author zhaoyang
 * @date July 2, 2014 6:20:35 PM
 * @version V1.0
 */
public enum InsuranceEvent {

  RefundSuccess("RefundSuccess", "退款成功"),
  RefundFailure("RefundFailure", "退款失败"), 
  DeliveringSuccess("DeliveringSuccess", "投保请求成功"),
  DeliveredSuccess("DeliveredSuccess", "投保成功"), 
  DeliveredFailure("DeliveredFailure", "投保失败"),
  DeliveringRequestAgain("DeliveringRequestAgain", "再次请求投保"), 
  ModifiedToDelivered("ModifiedToDelivered", "人工调整状态到投保成功"), 
  ModifiedToCancelSuccess("ModifiedToCancelSuccess", "人工调整状态到退保成功"), 
  RefundRequest("RefundRequest", "投保失败退款请求"), 
  CancelingRequest("CancelingRequest", "退保请求"), 
  CancelSuccess("CancelSuccess", "退保成功"), 
  CancelFailure("CancelFailure", "退保失败"), 
  CancelingRequestAgain("CancelingRequestAgain", "再次请求退保"),
  CancelingRequestFromMS("CancelingRequestFromMS","后台退保请求");

  private String name;

  private String desc;

  private InsuranceEvent(String name, String desc) {
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
