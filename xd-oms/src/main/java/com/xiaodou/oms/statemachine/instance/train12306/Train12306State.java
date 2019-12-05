package com.xiaodou.oms.statemachine.instance.train12306;

/**
 * 
 * 雷锋模式状态
 * 
 * @Description:火车票业务的订单状态
 * 
 * @author zhaoyang
 * @date June 16, 2014 3:24:35 PM
 * @version V1.0
 */
public enum Train12306State {
  Init(0, "Init", "新单"),
  InQueue(4,"inQueue","队列中"),
  Closed(5, "Closed", "取消"),
  HoldingSeat(7, "HoldingSeat", "占座成功"),
  Complete(2, "Complete", "订单完成");

  private Integer name;
  private String code;
  private String desc;

  private Train12306State(Integer name, String code, String desc) {
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
