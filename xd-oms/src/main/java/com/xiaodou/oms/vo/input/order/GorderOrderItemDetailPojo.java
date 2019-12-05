package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class GorderOrderItemDetailPojo extends BasePojo {
  
  /**
   * OrderItem主键
   */
  @NotEmpty(field="orderId", value = "null")
  private String orderItemId;
  
  /**
   * Order订单号
   */
  @NotEmpty(field="orderItemId", value = "null")
  private String orderId;
  
  
  /**
   * 下单账户
   */
  private String buyAccountId;

  
  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> gorderOutputProperties;
  
  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> orderOutputProperties;
  
  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> orderItemOutputProperties;

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

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Map<String, Object> getGorderOutputProperties() {
    return gorderOutputProperties;
  }

  public void setGorderOutputProperties(Map<String, Object> gorderOutputProperties) {
    this.gorderOutputProperties = gorderOutputProperties;
  }

  public Map<String, Object> getOrderOutputProperties() {
    return orderOutputProperties;
  }

  public void setOrderOutputProperties(Map<String, Object> orderOutputProperties) {
    this.orderOutputProperties = orderOutputProperties;
  }

  public Map<String, Object> getOrderItemOutputProperties() {
    return orderItemOutputProperties;
  }

  public void setOrderItemOutputProperties(Map<String, Object> orderItemOutputProperties) {
    this.orderItemOutputProperties = orderItemOutputProperties;
  }

  @Override
  public String toString() {
    return "GorderOrderItemDetailPojo{" +
            "orderItemId='" + orderItemId + '\'' +
            ", orderId='" + orderId + '\'' +
            ", buyAccountId='" + buyAccountId + '\'' +
            ", gorderOutputProperties=" + gorderOutputProperties +
            ", orderOutputProperties=" + orderOutputProperties +
            ", orderItemOutputProperties=" + orderItemOutputProperties +
            '}';
  }
}
