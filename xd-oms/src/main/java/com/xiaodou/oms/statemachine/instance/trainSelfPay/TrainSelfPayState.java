package com.xiaodou.oms.statemachine.instance.trainSelfPay;

/**
 * Date: 2015/1/7
 * Time: 11:03
 *
 * @author Tian.Dong
 */
public enum TrainSelfPayState {
  Init(0, "Init", "新单"),
  InQueue(4,"inQueue","队列中"),

  HoldingSeat(7, "HoldingSeat", "占座成功"),
  PaySuccess(1, "PaySuccess", "支付成功"),
  PayFailure(-1, "PayFailure", "支付失败"),
  Closed(5, "Closed", "取消"),
  Delivering(6, "Delivering", "12306支付中"),
  Pay12306Success(8, "Pay12306Success", "12306支付成功"),
  Delivered(2, "Delivered", "出票成功");

  private Integer name;
  private String code;
  private String desc;

  private TrainSelfPayState(Integer name, String code, String desc) {
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
