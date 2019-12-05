package com.xiaodou.oms.statemachine.instance.train12306;

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
public enum Train12306Event {
  EnterQueueSuccess("EnterQueueSuccess","进入队列成功"),
  EnterQueueFailure("EnterQueueFailure","进入队列失败"),
  CancelByUser("CancelByUser", "用户取消"),
  HoldingSeatSuccess("HoldingSeatSuccess","占座成功"),
  HoldingSeatFailure("HoldingSeatFailure", "占座失败"),
  SynWithPayTimeout("SynWithPayTimeout","状态同步取消"),
  SynWithComplete("SynWithComplete","状态同步完成"),
  RefundTicket("RefundTicket","退票"),
  HoldingSeatSuccessFromInit("HoldingSeatSuccessFromInit","占座成功");

  private String name;

  private String desc;

  private Train12306Event(String name, String desc) {
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
