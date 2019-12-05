package com.xiaodou.ucenter.enums;

/**
 * 
 * 获取验证码时状态枚举
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public enum CheckCodeStatusEnum {
  FifteenLimit(1, "当日验证码发送短信量已达上限"),
  OneMinuteNoRepeat(2, "一分钟内不能重发"),
  CheckCodeTypeError(3, "验证码类型错误"),
  PhoneNumError(4, "手机号无效");
  
  private Integer code;
  private String desc;
  
  private CheckCodeStatusEnum(Integer code, String desc) {
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
