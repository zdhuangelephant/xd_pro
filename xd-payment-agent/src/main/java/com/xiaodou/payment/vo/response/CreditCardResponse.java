package com.xiaodou.payment.vo.response;


public class CreditCardResponse extends ResponseBase {

  private String credit_card_no; // 信用卡卡号
  private String expire_date;// 信用卡过期时间
  private String verify_code;// 信用卡验证字符串
  private String card_holder;// 信用卡持卡人
  private String id_no; // 证件号
  private Integer id_type;// 证件类型, 8001身份证
  private Integer payment_product_id;//银行卡

  public String getCredit_card_no() {
    return credit_card_no;
  }

  public void setCredit_card_no(String credit_card_no) {
    this.credit_card_no = credit_card_no;
  }

  public String getExpire_date() {
    return expire_date;
  }

  public void setExpire_date(String expire_date) {
    this.expire_date = expire_date;
  }

  public String getVerify_code() {
    return verify_code;
  }

  public void setVerify_code(String verify_code) {
    this.verify_code = verify_code;
  }

  public String getCard_holder() {
    return card_holder;
  }

  public void setCard_holder(String card_holder) {
    this.card_holder = card_holder;
  }

  public String getId_no() {
    return id_no;
  }

  public void setId_no(String id_no) {
    this.id_no = id_no;
  }

  public Integer getId_type() {
    return id_type;
  }

  public void setId_type(Integer id_type) {
    this.id_type = id_type;
  }

  public Integer getPayment_product_id() {
    return payment_product_id;
  }

  public void setPayment_product_id(Integer payment_product_id) {
    this.payment_product_id = payment_product_id;
  }

}
