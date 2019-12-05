package com.xiaodou.oms.statemachine.instance.businsurance;

/**
 * 
 *@Description: 保险业务的事件
 *@author: zhenpu.hou
 *@date : 2015年5月11日 下午2:19:36
 */
public enum BusInsuranceEvent {

  RefundSuccess("RefundSuccess", "退款成功"),//23
  RefundFailure("RefundFailure", "退款失败"), //24
  DeliveringSuccess("DeliveringSuccess", "投保请求成功"),//13
  DeliveredSuccess("DeliveredSuccess", "出保成功"), //14  15
  DeliveredFailure("DeliveredFailure", "出保失败"),//16  18
  DeliveringRequestAgain("DeliveringRequestAgain", "重新投保"),//19
  ModifiedToDelivered("ModifiedToDelivered", "人工调整状态到出保成功"), //17
  RefundRequest("RefundRequest", "出保失败退款请求"), //20
  PaySuccess("PaySuccess","支付成功");//1

  private String name;

  private String desc;

  private BusInsuranceEvent(String name, String desc) {
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
