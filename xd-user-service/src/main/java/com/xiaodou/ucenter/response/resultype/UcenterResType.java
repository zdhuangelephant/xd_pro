package com.xiaodou.ucenter.response.resultype;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum UcenterResType {


  /**
   * Iterface : common/upToken Desc : 缺少accessKey/secretKey
   */
  MISSKEY("20101", "缺少accessKey/secretKey"),

  /**
   * 获取验证码时状态枚举
   */
  FifteenLimit("20201", "当日验证码发送短信量已达上限"), OneMinuteNoRepeat("20202", "一分钟内不能重发"), CheckCodeTypeError(
      "20203", "验证码类型错误"), PhoneNumError("20204", "手机号无效"),

  /**
   * 密码操作枚举
   */
  UnRegistered("20301", "尚未注册"), CheckCodeOutDate("20302", "验证码过期"), CheckCodeError("20303",
      "验证码错误"), UpdatePasswordFail("20304", "密码更新失败"), UnLoginUser("20305", "用户尚未登录，请先登录"), UnUserExisted(
      "20306", "用户不存在"), OldPwdError("20307", "原密码不正确"), TwicePwdError("20308", "两次密码不一致"), EnteredPasswordsDiffer(
      "20309", "两次输入密码不一致"), HasRegisterd("20310", "您已经注册过，请直接登录"), NotFindMajor("20311",
      "没有找到您的专业"), checkCodeError("20312", "验证码无效"), PwdError("20313", "密码无效"), HasNickName(
      "20314", "昵称已被使用"),

  /**
   * 登录接口返回状态枚举
   */
  NoFoundUser("20401", "用户不存在"), PasswordError("20402", "密码错误"), DeviceIdReachLimit("20403",
      "设备号已达上限"), TokenUpdateFail("20404", "token更新失败"),

  /**
   * 修改个人资料状态枚举
   */
  UserUpdateFail("20405", "修改个人资料失败"),

  /**
   * 公告|帮助返回状态枚举
   */
  NoFoundHelp("20406", "暂无记录"), NoFoundNotice("20407", "暂无公告"),

  /**
   * checkToken返回状态枚举
   */
  NoTokenExisted("20408", "token值不能为空"), UnAbleToken("20409", "登陆超时，请重新登陆。"), UnLoginDevice(
      "20409", "设备未登录"),
  /**
   * 意见反馈返回状态枚举
   */
  FeekBackFail("20450", "意见反馈失败，请重试"),
  /**
   * 必填信息不完整
   */
  UnCompleteInfo("00007", "必填信息不完整"),
  /**
   * 系统公告和活动返回状态枚举More than one data
   */
  MoreThanOneData("20451", "查询出的数据超过一条"),
  /**
   * 用户赞过目标用户
   */
  HasPraised("20452", "用户赞过目标用户"),
  /** TargetUserMissed 目标用户不存在 */
  TargetUserMissed("20501", "目标用户不存在"),
  /** HasBeenFriend 目标用户已经是好友关系 */
  HasBeenFriend("20502", "目标用户已经是好友关系"),
  /** NotFriend 目标用户不是好友关系 */
  NotFriend("20503", "目标用户不是好友关系"),
  /** TargetIsDeviced 目标用户是游客 */
  TargetIsDeviced("20504", "不能添加游客为好友"),
  /** TargetIsSelf 目标用户是自己 */
  TargetIsSelf("20505", "不能添加自己为好友"),
  /** TargetMessageMissed 目标消息不存在 */
  TargetMessageMissed("20901", "目标消息不存在"),
  /** TargetMessageNotInit 目标消息非初始状态 */
  TargetMessageNotInit("20902", "目标消息非初始状态"),

  /** PayAmountFail 支付失败 */
  PayAmountFail("21001", "支付失败"),
  /* 更新金币数失败 */
  UpdateUserCoinFail("21101", "更新金币数失败");



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
