package com.xiaodou.ucenter.domain.response.resultype;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum UcenterResType {



  PhoneNumError("20204", "手机号无效"),

  /**
   * 密码操作枚举
   */
  UnRegistered("20301", "尚未注册"), CheckCodeOutDate("20302", "验证码过期"), CheckCodeError("20303",
      "验证码错误"), UpdatePasswordFail("20304", "密码更新失败"), UnLoginUser("20305", "用户尚未登录，请先登录"), UnUserExisted(
      "20306", "用户不存在"), OldPwdError("20307", "原密码不正确"), TwicePwdError("20308", "两次密码不一致"), EnteredPasswordsDiffer(
      "20309", "两次输入密码不一致"), HasRegisterd("20310", "您已经注册过，请直接登录"), NotFindMajor("20311",
      "没有找到您的专业"), checkCodeError("20312", "验证码无效"), PwdError("20313", "密码无效"), PwdFit("20314", "密码一致"),
      EqualDefaultPassword("20315", "密码与初始密码相同"),

  /**
   * 登录接口返回状态枚举
   */
  NoFoundUser("20401", "用户不存在"), PasswordError("20402", "密码错误"), DeviceIdReachLimit("20403",
      "设备号已达上限"), TokenUpdateFail("20404", "token更新失败");


  /**
   * 结果码
   */
  private String code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 服务器Ip
   */
  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  /**
   * Constract
   * 
   * @param code 结果码
   * @param msg 提示信息
   * @throws UnknownHostException
   */
  UcenterResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }

}
