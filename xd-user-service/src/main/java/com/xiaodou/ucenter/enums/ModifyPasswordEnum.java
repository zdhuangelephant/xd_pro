package com.xiaodou.ucenter.enums;

/**
 * 
 * 修改密码枚举
 *
 * @author      peiru.zhang 
 * @version     1.0  
 * @since       JDK1.7
 */
public enum ModifyPasswordEnum {
  UnLoginUser(1,"用户尚未登录，请先登录"),
  UnUserExisted(2, "用户不存在"),
  OldPwdError(3, "原密码不正确"),
  TwicePwdError(4, "两次密码不一致"),
  UpdatePasswordFail(5, "更新密码失败");

  private Integer code;
  private String desc;
  private ModifyPasswordEnum(Integer code, String desc) {
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
