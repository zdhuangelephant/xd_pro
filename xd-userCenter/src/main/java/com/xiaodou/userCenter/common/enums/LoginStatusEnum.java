package com.xiaodou.userCenter.common.enums;

/**
 * 
 * 登录接口返回状态枚举
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public enum LoginStatusEnum {
  NoFoundUser(1, "用户不存在"),
  PasswordError(2, "密码错误"),
  DeviceIdReachLimit(3, "设备号已达上限"),
  TokenUpdateFail(4, "token更新失败");
  
  private Integer code;
  private String desc;
  
  private LoginStatusEnum(Integer code, String desc) {
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
