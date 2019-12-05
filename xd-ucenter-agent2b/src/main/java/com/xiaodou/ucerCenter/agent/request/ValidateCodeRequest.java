package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.userCenter.request.ValidateCodeRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月26日
 * @description 验证验证码请求pojo
 * @version 1.0
 */
public class ValidateCodeRequest extends BaseRequest {

  /**
   * 用户手机号
   */
  @NotEmpty
  private String phoneNum;

  /**
   * 验证码类型 1：注册验证码 2：找回密码验证码
   */
  @NotEmpty
  private String checkCodeType;

  /** checkCode 验证码码值 */
  @NotEmpty
  private String checkCode;

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getCheckCodeType() {
    return checkCodeType;
  }

  public void setCheckCodeType(String checkCodeType) {
    this.checkCodeType = checkCodeType;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

}
