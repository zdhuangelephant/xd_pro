package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Date: 2014/10/22
 * Time: 11:24
 *
 * @author Tian.Dong
 */
public class GetPayDetailRequest {
  @JSONField(name="business_type")
  private int businessType;
  @JSONField(name="order_id")
  private String orderId;

  public int getBusinessType() {
    return businessType;
  }

  public void setBusinessType(int businessType) {
    this.businessType = businessType;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
