package com.xiaodou.server.pay.payplatform.dc.wxpay;

public class WxPayDto {

  private String businessType;// 业务线
  private String orderId;// 订单号
  private String totalFee;// 金额
  private String spbillCreateIp;// 订单生成的机器 IP
  private String notifyUrl;// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
  private String body;// 商品描述根据情况修改
  private String openId;// 微信用户对一个公众号唯一

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public void setBusinessType(Integer businessType) {
    if (null == businessType) throw new IllegalArgumentException("businessType can't be null");
    this.businessType = businessType.toString();
  }

  /**
   * @return the orderId
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * @param orderId the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the totalFee
   */
  public String getTotalFee() {
    return totalFee;
  }

  /**
   * @param totalFee the totalFee to set
   */
  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }

  /**
   * @return the spbillCreateIp
   */
  public String getSpbillCreateIp() {
    return spbillCreateIp;
  }

  /**
   * @param spbillCreateIp the spbillCreateIp to set
   */
  public void setSpbillCreateIp(String spbillCreateIp) {
    this.spbillCreateIp = spbillCreateIp;
  }

  /**
   * @return the notifyUrl
   */
  public String getNotifyUrl() {
    return notifyUrl;
  }

  /**
   * @param notifyUrl the notifyUrl to set
   */
  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @param body the body to set
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * @return the openId
   */
  public String getOpenId() {
    return openId;
  }

  /**
   * @param openId the openId to set
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
