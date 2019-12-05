package com.xiaodou.server.mapi.request.ucenter;

import java.sql.Timestamp;
import java.util.UUID;

import com.xiaodou.server.mapi.util.RandomNumberUtil;
import com.xiaodou.server.mapi.util.UcenterUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NewRegistAccountRequest extends UserBaseInfo {

  /**
   * 手机号
   */
  @NotEmpty
  private String phoneNum;

  /**
   * 密码
   */
  @NotEmpty
  private String passWord;

  /**
   * 确认密码
   */
  @NotEmpty
  private String confirmPassWord;

  /** salt 注册时系统自动生成掩码 */
  private String salt = RandomNumberUtil.getRandomString(4, 6);

  public NewRegistAccountRequest() {}

  public Timestamp getTokenTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  public String getPasswd() {
    return UcenterUtil.getPasswd(getPassWord() + getSalt());
  }

  public String getSalt() {
    return salt;
  }

  public Timestamp getCreateTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  public String getToken() {
    return UUID.randomUUID().toString();
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getConfirmPassWord() {
    return confirmPassWord;
  }

  public void setConfirmPassWord(String confirmPassWord) {
    this.confirmPassWord = confirmPassWord;
  }

}
