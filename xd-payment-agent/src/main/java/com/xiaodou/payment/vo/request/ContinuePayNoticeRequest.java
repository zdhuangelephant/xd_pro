package com.xiaodou.payment.vo.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Date: 2014/11/18
 * Time: 15:46
 *
 * @author Tian.Dong
 */
public class ContinuePayNoticeRequest {
  /**
   * 业务线
   */
  @JSONField(name = "business_type")
  private Integer businessType;
  /**
   * 请求交易号
   */
  @JSONField(name="trade_no")
  private long tradeNo;
  /**
   * 业务订单号
   */
  @JSONField(name="order_id")
  private String orderId;
  /**
   * 是否继续支付
   */
  @JSONField(name="continue_pay")
  private int continuePay;
  /**
   * 原因
   */
  @JSONField(name="check_info")
  private String checkInfo;

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public long getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(long tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getContinuePay() {
    return continuePay;
  }

  public void setContinuePay(int continuePay) {
    this.continuePay = continuePay;
  }

  public String getCheckInfo() {
    return checkInfo;
  }

  public void setCheckInfo(String checkInfo) {
    this.checkInfo = checkInfo;
  }
}
