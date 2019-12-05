package com.xiaodou.oms.statemachine.instance.selftaught.product;

/**
 * @name @see com.xiaodou.oms.statemachine.instance.selftaught.product.StProductEvent.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月1日
 * @description 自考产品订单事件
 * @version 1.0
 */
public enum StProductEvent {
  /** PaySuccess 支付成功 */
  PaySuccess("PaySuccess", "支付成功"),
  /** PayFailure 支付失败 */
  PayFailure("PayFailure", "支付失败"),
  /** PayTimeout 支付超时 */
  PayTimeout("PayTimeout", "支付超时"),
  /** CancelByUser 用户取消 */
  CancelByUser("CancelByUser", "用户取消"),
  /** DeliveredSuccess 购买成功 */
  DeliveredSuccess("DeliveredSuccess", "购买成功");

  private String name;

  private String desc;

  private StProductEvent(String name, String desc) {
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
