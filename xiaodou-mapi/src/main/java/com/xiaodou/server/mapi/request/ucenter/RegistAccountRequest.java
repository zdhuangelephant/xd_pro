package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.util.RandomNumberUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class RegistAccountRequest extends UserBaseInfo {

  /**
   * 手机号
   */
  @NotEmpty
  private String phoneNum;

  /**
   * 密码
   */
  @NotEmpty
  private String password;

  /**
   * 确认密码
   */
  @NotEmpty
  private String confirmPassword;

  /**
   * 验证码
   */
  @NotEmpty
  private String checkCode;

  /** salt 注册时系统自动生成掩码 */
  private String salt = RandomNumberUtil.getRandomString(4, 6);

  public RegistAccountRequest() {}


  public String getSalt() {
    return salt;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

}
