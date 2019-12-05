package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Date: 2014/10/22
 * Time: 11:24
 *
 * @author Tian.Dong
 */
public class GetPayStatusRequest {
  @JSONField(name="business_type")
  private int businessType;
  @JSONField(name="trade_no")
  private String tradeNo;
  @JSONField(name="order_id")
  private String orderId;

  public int getBusinessType() {
    return businessType;
  }

  public void setBusinessType(int businessType) {
    this.businessType = businessType;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
