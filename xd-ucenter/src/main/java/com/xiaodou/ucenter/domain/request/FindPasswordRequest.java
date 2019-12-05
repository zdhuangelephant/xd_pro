package com.xiaodou.ucenter.domain.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * 通过手机号找回密码
 * 
 * @author 李德洪
 * @date 2017年2月10日
 */
public class FindPasswordRequest extends BaseValidatorPojo {
  @NotEmpty
  private String telephone;
  @NotEmpty
  private String password;

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
