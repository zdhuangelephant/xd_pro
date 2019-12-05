package com.xiaodou.oms.statemachine.instance.businsurance;

/**
 * 
 *@Description: 保险业务的事件
 *@author: zhenpu.hou
 *@date : 2015年5月11日 下午2:19:24
 */
public enum BusInsuranceState {

  Init(0, "Init", "新单"), 
  PaySuccess(1, "PaySuccess", "支付成功"), 
  PayFailure(-1, "PayFailure", "支付失败"), 
  Closed(5, "Closed", "取消"), 
  DeliveringSuccess(6,"DeliveringSuccess","投保成功"),
  Delivered(2,"Delivered","出保成功"),
  DeliveredFailure(-2,"DeliveredFailure","出保失败");

  private Integer name;
  private String code;
  private String desc;

  private BusInsuranceState(Integer name, String code, String desc) {
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
