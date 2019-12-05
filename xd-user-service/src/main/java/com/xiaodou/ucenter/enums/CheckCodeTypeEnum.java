package com.xiaodou.ucenter.enums;

/**
 * 
 * 验证码类型
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public enum CheckCodeTypeEnum {
  UserRegister("1", "用户注册验证码"),
  FindBackPassword("2", "找回密码验证码");
  
  private String code;
  private String desc;
  
  private CheckCodeTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
  
}
