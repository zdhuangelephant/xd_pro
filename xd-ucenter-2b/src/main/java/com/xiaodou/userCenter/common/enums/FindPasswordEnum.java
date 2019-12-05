package com.xiaodou.userCenter.common.enums;

/**
 * 
 * 找回密码枚举
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public enum FindPasswordEnum {
  UnRegistered(1, "尚未注册"),
  CheckCodeOutDate(2, "验证码过期"),
  CheckCodeError(3, "验证码错误"),
  UpdatePasswordFail(4, "密码更新失败");
  
  private Integer code;
  private String desc;
  
  private FindPasswordEnum(Integer code, String desc) {
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
