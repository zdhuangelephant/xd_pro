package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class LoginRequest extends BaseRequest {

  @NotEmpty
  private String phoneNum;

  @NotEmpty
  private String pwd;

  @NotEmpty
  private String registrationId;

  public LoginRequest getLoginRequest(NewLoginRequest pojo) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setClientIp(pojo.getClientIp());
    loginRequest.setClientType(pojo.getClientType());
    loginRequest.setDeviceId(pojo.getDeviceId());
    loginRequest.setModule(pojo.getModule());
    loginRequest.setPhoneNum(pojo.getPhoneNum());
    loginRequest.setPwd(pojo.getPwd());
    loginRequest.setRegistrationId(pojo.getRegistrationId());
    loginRequest.setSessionToken(pojo.getSessionToken());
    loginRequest.setVersion(pojo.getVersion());
    return loginRequest;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }

}
