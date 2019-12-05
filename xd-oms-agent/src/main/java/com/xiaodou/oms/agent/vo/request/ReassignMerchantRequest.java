package com.xiaodou.oms.agent.vo.request;


/**
 * Created by zyp on 14-9-1.
 */
public class ReassignMerchantRequest extends BaseRequest {
  /**
   * 订单号
   */
  private String orderId;

  /**
   * 下单账户
   */
  private String buyAccountId;

  /**
   * 合作商ID
   */
  private Integer merchantId;

  /**
   * 合作商名称
   */
  private String merchantName;

  /**
   * 合作商电话
   */
  private String merchantTel;

  /**
   * 客户端IP
   */
  private String clientIp;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public Integer getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Integer merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getMerchantTel() {
    return merchantTel;
  }

  public void setMerchantTel(String merchantTel) {
    this.merchantTel = merchantTel;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }
}
