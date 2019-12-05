package com.xiaodou.oms.statemachine.instance.insurance;

/**
 * 
 * @Title:InsuranceState.java
 * 
 * @Description:保险业务的事件
 * 
 * @author zhaoyang
 * @date July 2, 2014 6:20:35 PM
 * @version V1.0
 */
public enum InsuranceState {

  Init(0, "Init", "新单"), 
  PaySuccess(1, "PaySuccess", "支付成功"), 
  PayFailure(-1, "PayFailure", "支付失败"), 
  Closed(5, "Closed", "取消"), 
  Delivering(6,"Delivering","投保中"),
  Delivered(2,"Delivered","投保成功"),
  DeliveredFailure(-2,"DeliveredFailure","投保失败"),
  Canceling(4,"Canceling","退保中"),
  CancelSuccess(7,"CancelSuccess","退保成功"),
  CancelFailure(-4,"CancelFailure","退保失败") ;

  private Integer name;
  private String code;
  private String desc;

  private InsuranceState(Integer name, String code, String desc) {
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
