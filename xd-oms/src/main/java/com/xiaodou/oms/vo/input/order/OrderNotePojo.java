package com.xiaodou.oms.vo.input.order;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class OrderNotePojo extends BasePojo {
  
  /**
   * 订单号
   */
  @NotEmpty
  private String orderId;
  
  /**
   * 备注内容
   */
  @NotEmpty
  private String note;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
