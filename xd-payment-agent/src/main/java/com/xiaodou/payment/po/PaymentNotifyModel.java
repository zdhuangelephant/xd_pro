package com.xiaodou.payment.po;

/**
 * payment回调model
 */
public class PaymentNotifyModel {

  /**
   * 商户号，payment分配
   */
  private String merchant_id;

  /**
   * 签名类型 取值: MD5、RSA,默认: MD5
   */
  private String sign_type;

  /**
   * 签名
   */
  private String sign;

  /**
   * token
   */
  private String trade_no;

  /**
   * 支付时间
   */
  private String pay_time;

  /**
   * 支付状态,0-失败；1-成功
   */
  private Integer result_status;

  /**
   * 支付结果详情: 成功是为空，失败错误信息
   */
  private String error_info;

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

  public String getTrade_no() {
    return trade_no;
  }

  public void setTrade_no(String trade_no) {
    this.trade_no = trade_no;
  }

  public String getPay_time() {
    return pay_time;
  }

  public void setPay_time(String pay_time) {
    this.pay_time = pay_time;
  }

  public Integer getResult_status() {
    return result_status;
  }

  public void setResult_status(Integer result_status) {
    this.result_status = result_status;
  }

  public String getError_info() {
    return error_info;
  }

  public void setError_info(String error_info) {
    this.error_info = error_info;
  }
}
