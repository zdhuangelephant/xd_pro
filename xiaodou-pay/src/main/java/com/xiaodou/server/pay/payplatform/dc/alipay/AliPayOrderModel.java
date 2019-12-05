package com.xiaodou.server.pay.payplatform.dc.alipay;

/**
 * 
 * @name AliPayOrderResponse
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月29日
 * @description 阿里安全支付，服务端生成 订单以及签名 返回bean
 * @version 1.0
 */
public class AliPayOrderModel {
  private String partner;
  private String seller_id;
  private String out_trade_no;
  private String subject;
  private String body;
  private String total_fee;
  private String notify_url;
  private String service;
  private String payment_type;
  private String _input_charset;
  private String it_b_pay;
  private String sign;
  private String sign_type;

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

  public String getSeller_id() {
    return seller_id;
  }

  public void setSeller_id(String seller_id) {
    this.seller_id = seller_id;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getTotal_fee() {
    return total_fee;
  }

  public void setTotal_fee(String total_fee) {
    this.total_fee = total_fee;
  }

  public String getNotify_url() {
    return notify_url;
  }

  public void setNotify_url(String notify_url) {
    this.notify_url = notify_url;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getPayment_type() {
    return payment_type;
  }

  public void setPayment_type(String payment_type) {
    this.payment_type = payment_type;
  }

  public String get_input_charset() {
    return _input_charset;
  }

  public void set_input_charset(String _input_charset) {
    this._input_charset = _input_charset;
  }

  public String getIt_b_pay() {
    return it_b_pay;
  }

  public void setIt_b_pay(String it_b_pay) {
    this.it_b_pay = it_b_pay;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getSign_type() {
    return sign_type;
  }

  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

  // partner="2088101568358171"&seller_id="xxx@alipay.com"&
  // out_trade_no="0819145412-6177"&subject="测试"&body="测试测试"&
  // total_fee="0.01"&notify_url="http://notify.msp.hk/notify.htm"&
  // service="mobile.securitypay.pay"&payment_type="1"&
  // _input_charset="utf-8"&
  // it_b_pay="30m"&
  // sign="lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D"&
  // sign_type="RSA";

}
