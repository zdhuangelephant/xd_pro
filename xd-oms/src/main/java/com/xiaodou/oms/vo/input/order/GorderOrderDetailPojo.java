package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class GorderOrderDetailPojo extends BasePojo {
  
  /**
   * Order订单号
   */
  @NotEmpty
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
  
  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> orderOutputProperties;

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
}
