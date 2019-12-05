package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class CheckCodeRequest extends BaseRequest {

  /**
   * 用户手机号
   */
  @NotEmpty
  private String phoneNum;

  /**
   * 验证码类型 1：注册验证码 2：找回密码验证码
   */
  @NotEmpty
  @LegalValueList({"1", "2"})
  private String checkCodeType;

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
}
