package com.xiaodou.oms.vo.input.order;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ItemNotePojo extends BasePojo {
  /**
   * 订单号
   */
  @NotEmpty
  private String orderItemId;

  /**
   * 备注内容
   */
  @NotEmpty
  private String note;

  public String getOrderItemId() {
    return orderItemId;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
