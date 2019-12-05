package com.xiaodou.userCenter.response.resultype;

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
      "没有找到您的专业"), checkCodeError("20312", "验证码无效"), PwdError("20313", "密码无效"), EqualDefaultPassword(
      "20314", "密码与初始密码相同"), CHECK_CODE_TYPE_WRONG("20315", "验证码类型错误"), IS_KEYWORD("20316",
      "该词为关键字~"),

  /**
   * 登录接口返回状态枚举
   */
  NoFoundUser("20401", "用户不存在"), PasswordError("20402", "密码错误"), DeviceIdReachLimit("20403",
      "设备号已达上限"), TokenUpdateFail("20404", "token更新失败"), NeedUploadArae("20405", "需要上传经纬度"),
      LOGIN_DUPLICATE("20406","您的账号正在其它设备尝试登录中，请稍后重试登录"),

  /**
   * checkToken返回状态枚举
   */
  NoTokenExisted("20408", "token值不能为空"), UnAbleToken("20409", "登陆超时，请重新登陆。"), UnLoginDevice(
    "20410", "设备未登录"),
    
  /**
   * 修改个人资料状态枚举
   */
  UserUpdateFail("20420", "修改个人资料失败"), UnFindFace("20421", "未检测到人脸"),

  /**
   * 公告|帮助返回状态枚举
   */
  NoFoundHelp("20430", "暂无记录"), NoFoundNotice("20431", "暂无公告"),


  /** NotImportUser 非导入用户 */
  NotImportUser("20440", "非导入用户无权操作"),

  /**
   * 意见反馈返回状态枚举
   */
  FeekbackFail("20450", "意见反馈失败，请重试"), CategorIsNull("20451", "请传入意见反馈类型"), JSON_FAIL("20452",
      "json解析异常"), MORE_THAN_EXCEED("20453", "反馈信息不能超过1000字符"),

  /**UnCompleteInfo 必填信息不完整*/
  UnCompleteInfo("00007", "必填信息不完整"),
  /**MoreThanOneData 系统公告和活动返回状态枚举More than one data*/
  MoreThanOneData("20460", "查询出的数据超过一条"),
  /**HasPraised 用户赞过目标用户*/
  HasPraised("20470", "用户赞过目标用户"),
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
  UpdateUserCoinFail("21101", "更新金币数失败"),
  /* 更新金币数失败 */
  AccountClosure("21201", "账号异常，暂停服务24小时，如有疑问请联系客服。"), DeviceClosure("21202",
      "设备异常，暂停服务24小时，如有疑问请联系客服。"), AccountDeviceClosure("21203", "账号或设备异常，暂停服务24小时，如有疑问请联系客服。"), USER_CREDIT_RANK_IS_NULL(
      "21301", "用户积分列表为空"),

  BASE_USER_IS_NULL("21401", "用户基础数据不存在，请联系客服人员处理~");

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
