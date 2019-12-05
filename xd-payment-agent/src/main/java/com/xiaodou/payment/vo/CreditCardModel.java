package com.xiaodou.payment.vo;

import com.xiaodou.payment.vo.response.CreditCardResponse;

/**
 * 支付平台的信用卡信息
 *
 * @author Jiejun.Gao
 */
public class CreditCardModel {
  private double cc_amt;
  private double cc_customer_service_amt;
  private String credit_card_no;
  private String expire_date;
  private String verify_code;
  private String card_holder;
  private Integer id_type;
  private String id_no;
  private Integer cc_currency;


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

  public Integer getId_type() {
    return id_type;
  }

  public void setId_type(Integer id_type) {
    this.id_type = id_type;
  }

  public String getId_no() {
    return id_no;
  }

  public void setId_no(String id_no) {
    this.id_no = id_no;
  }

  public Integer getCc_currency() {
    return cc_currency;
  }

  public void setCc_currency(Integer cc_currency) {
    this.cc_currency = cc_currency;
  }

  public String getCredit_card_no() {
    return credit_card_no;
  }

  public void setCredit_card_no(String credit_card_no) {
    this.credit_card_no = credit_card_no;
  }

  public void setCreditCardResponse(CreditCardResponse creditCard) {
    this.card_holder = creditCard.getCard_holder();
    this.credit_card_no = creditCard.getCredit_card_no();
    this.expire_date = creditCard.getExpire_date();
    this.id_no = creditCard.getId_no();
    this.id_type = creditCard.getId_type();
    this.verify_code = creditCard.getVerify_code();
  }

  public double getCc_amt() {
    return cc_amt;
  }

  public void setCc_amt(double cc_amt) {
    this.cc_amt = cc_amt;
  }

  public double getCc_customer_service_amt() {
    return cc_customer_service_amt;
  }

  public void setCc_customer_service_amt(double cc_customer_service_amt) {
    this.cc_customer_service_amt = cc_customer_service_amt;
  }

}
