package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class OrderItemDetailPojo extends BasePojo {

  /**
   * OrderItem主键
   */
  @NotEmpty
  private String orderItemId;

  /**
   * 下单账户
   */
  private String buyAccountId;

  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> outputProperties;

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public String getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(String orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Map<String, Object> getOutputProperties() {
    return outputProperties;
  }

  public void setOutputProperties(Map<String, Object> outputProperties) {
    this.outputProperties = outputProperties;
  }

}
