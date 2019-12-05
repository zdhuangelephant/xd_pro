package com.xiaodou.userCenter.common.enums;

/**
 * 
 * 注册接口状态码枚举
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public enum RegisterStatusEnum {
  
  EnteredPasswordsDiffer(1, "两次输入密码不一致"),
  HasRegisterd(2, "您已经注册过，请直接登录"),
  CheckCodeOutDate(3, "验证码已过期"),
  CheckCodeError(4, "验证码错误"),
  PhoneNumError(5, "手机号无效");
  
  private Integer code;
  private String desc;
  
  private RegisterStatusEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
