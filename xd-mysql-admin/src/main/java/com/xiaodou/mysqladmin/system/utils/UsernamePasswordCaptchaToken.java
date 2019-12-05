package com.xiaodou.mysqladmin.system.utils;

public class UsernamePasswordCaptchaToken {
  private String captcha;

  public String getCaptcha() {
    return this.captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public UsernamePasswordCaptchaToken() {}

  public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe,
      String host, String captcha) {
    this.captcha = captcha;
  }
}
