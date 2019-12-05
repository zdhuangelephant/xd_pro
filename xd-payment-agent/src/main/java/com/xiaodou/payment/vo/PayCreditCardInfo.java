package com.xiaodou.payment.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * CC支付的信息
 *
 * @author Jiejun.Gao
 */
public class PayCreditCardInfo {
  /**
   * payment分配的业务类型
   */
  private Integer business_type;
  /**
   * payment分配的商户号
   */
  private String merchant_id;
  /**
   * payment分配的key
   */
  @JSONField(serialize=false,deserialize=true)
  private String key;
  /**
   * 签名类型 如MD5
   */
  private String sign_type;
  /**
   * 签名
   */
  private String sign;

  /**
   * 支付类型，如混合支付，CC,CA,DC
   */
  private Integer mixpayment_type;
  /**
   * 即token
   */
  private String trade_no;
  /**
   * 给payment的订单号
   */
  private String order_id;
  /**
   * 订单来源，如iphone
   */
  private String order_from;
  /**
    总金额
   */
  private double total_amt;//两位小数
  private String notify_url;
  private Integer pay_from;
  private Integer payment_product_id;
  private CreditCardModel cc;

  public String getMerchant_id() {
    return merchant_id;
  }

  public void setMerchant_id(String merchant_id) {
    this.merchant_id = merchant_id;
  }

  public String getSign_type() {
    return sign_type;
  }

  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public Integer getBusiness_type() {
    return business_type;
  }

  public void setBusiness_type(Integer business_type) {
    this.business_type = business_type;
  }

  public Integer getMixpayment_type() {
    return mixpayment_type;
  }

  public void setMixpayment_type(Integer mixpayment_type) {
    this.mixpayment_type = mixpayment_type;
  }

  public String getTrade_no() {
    return trade_no;
  }

  public void setTrade_no(String trade_no) {
    this.trade_no = trade_no;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getOrder_from() {
    return order_from;
  }

  public void setOrder_from(String order_from) {
    this.order_from = order_from;
  }

  public CreditCardModel getCc() {
    return cc;
  }

  public void setCc(CreditCardModel cc) {
    this.cc = cc;
  }

  public void setCc(CreditCardInfo cc) {
    this.cc = new CreditCardModel();
    this.cc.setCc_amt(cc.getAmt());
    this.cc.setCard_holder(cc.getCardHolder());
    this.cc.setCredit_card_no(cc.getCardNo());
    this.cc.setCc_currency(cc.getCurrentcy());
    this.cc.setCc_customer_service_amt(cc.getServiceAmt());//手续费
    this.cc.setExpire_date(cc.getEcpireDate());
    this.cc.setId_no(cc.getIdNo());
    this.cc.setId_type(cc.getIdType());
    this.cc.setVerify_code(cc.getVerifyCode());
  }

  public String getNotify_url() {
    return notify_url;
  }

  public void setNotify_url(String notify_url) {
    this.notify_url = notify_url;
  }

  public Integer getPay_from() {
    return pay_from;
  }

  public void setPay_from(Integer pay_from) {
    this.pay_from = pay_from;
  }

  public Integer getPayment_product_id() {
    return payment_product_id;
  }

  public void setPayment_product_id(Integer payment_product_id) {
    this.payment_product_id = payment_product_id;
  }

  public void setOrderInfo(OrderInfo order) {
    this.notify_url = order.getNotifyUrl();
    this.order_from = order.getOrderFrom();//订单来源
    this.order_id = order.getOrderId();
    this.total_amt = order.getTotalAmt();//交易钱数
    this.pay_from = order.getPayFrom();//
    this.payment_product_id = order.getPaymentProductId();//
  }



  public double getTotal_amt() {
    return total_amt;
  }

  public void setTotal_amt(double total_amt) {
    this.total_amt = total_amt;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String inoutInfo() {
    StringBuilder sBuilder = new StringBuilder();
    sBuilder.append("business_type=").append(this.getBusiness_type())//业务类型
        .append("&card_holder=").append(this.getCc().getCard_holder())//持卡人
        .append("&cc_amt=").append(this.getCc().getCc_amt()) //交易金额
        .append("&cc_currency=").append(this.getCc().getCc_currency())  //交易币种
        .append("&cc_customer_service_amt=").append(this.getCc().getCc_customer_service_amt())//手续费
        .append("&merchant_id=").append(this.getMerchant_id())      //商户
        .append("&mixpayment_type=").append(this.getMixpayment_type())  //混合模式
        .append("&notify_url=").append(this.getNotify_url())             //回调url
        .append("&order_from=").append(this.getOrder_from())             //订单来源
        .append("&order_id=").append(this.getOrder_id())               //订单号
        .append("&pay_from=").append(this.getPay_from())               //支付来源
        .append("&payment_product_id=").append(this.getPayment_product_id())     //支付产品ID
        .append("&sign_type=").append(this.getSign_type())   //签名方式
        .append("&total_amt=").append(this.getTotal_amt())//支付订单金额
        .append("&trade_no=").append(this.getTrade_no());//支付流水号
    return sBuilder.toString();
  }

  /**
   * 根据产品线
   * 设置给payment传递的业务线，商户号和key
   *
   * @param productLine
   */
  public void setPayMerchant(String productLine) {
    this.business_type = PayMerchant.getBusinessType(productLine);
    this.merchant_id = PayMerchant.getMerchantId(productLine);
    this.key = PayMerchant.getKey(productLine);
    this.mixpayment_type = PayMerchant.getMixpaymentType();
    this.sign_type = PayMerchant.getSignType();
  }

}
