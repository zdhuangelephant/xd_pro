package com.xiaodou.oms.statemachine.instance.train;

/**
 *
 * @Title:TrainState.java
 *
 * @Description:火车票业务的订单状态
 *
 * @author zhaoyang
 * @date June 16, 2014 3:24:35 PM
 * @version V1.0
 */
public enum TrainState {
  Init(0, "Init", "新单"),
  InQueue(4, "InQueue", "占座中"),
  HoldingSeat(7, "HoldingSeat", "占座成功"),
  PaySuccess(1, "PaySuccess", "支付成功"),
  PayFailure(-1, "PayFailure", "支付失败"),
  Closed(5, "Closed", "取消"),
  Delivering(6, "Delivering", "出票中"),
  Delivered(2, "Delivered", "出票成功"),
  WaitingSecondPay(10, "WaitingSecondPay", "待退差价");

  private Integer name;
  private String code;
  private String desc;

  private TrainState(Integer name, String code, String desc) {
    this.name = name;
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getName() {
    return name;
  }

  public void setName(Integer name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return this.getName().toString();
  }

}
