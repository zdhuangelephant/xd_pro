package com.xiaodou.ms.web.request.admin;

import org.springframework.validation.Errors;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * Created by zyp on 14-9-25.
 */
public class EditInfo extends BaseRequest {
  /**
   * 邮件
   */
  private String email;

  /**
   * 手机号码
   */
  private String telphone;

  /**
   * 真实姓名
   */
  private String realName;

  /**
   * 学习机构
   */
  private String merchant;

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelphone() {
    return telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  @Override
  public void validate(Object o, Errors errors) {
    // super.validate(o, errors);
  }
}
