package com.xiaodou.oms.statemachine.instance.selftaught.product;

/**
 * @name @see com.xiaodou.oms.statemachine.instance.selftaught.product.StProductState.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月1日
 * @description 自考产品订单状态
 * @version 1.0
 */
public enum StProductState {
  /** Init 新单 */
  Init(0, "Init", "新单"),
  /** PaySuccess 支付成功 */
  PaySuccess(1, "PaySuccess", "支付成功"),
  /** Closed 取消 */
  Closed(5, "Closed", "取消"),
  /** Delivered 购买成功 */
  Delivered(2, "Delivered", "购买成功");

  private Integer name;
  private String code;
  private String desc;

  private StProductState(Integer name, String code, String desc) {
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
