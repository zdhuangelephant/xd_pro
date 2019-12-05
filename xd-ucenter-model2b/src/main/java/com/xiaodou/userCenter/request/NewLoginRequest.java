package com.xiaodou.userCenter.request;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.util.UcenterUtil;

public abstract class NewLoginRequest extends UserBaseInfo {
  /*
   * 第三方登录必填项
   */

  // local/qq/weibo/weixin;所属平台; device;游客模式登录
  @LegalValueList({UcenterModelConstant.PLATFORM_LOCAL, UcenterModelConstant.PLATFORM_QQ,
      UcenterModelConstant.PLATFORM_WEIBO, UcenterModelConstant.PLATFORM_WEIXIN,
      UcenterModelConstant.PLATFORM_DEVICE})
  private String platform;

  // 唯一标识，第三方
  @OrNotEmptyList({@NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_QQ),
      @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIBO),
      @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIXIN)})
  private String uniqueId;

  /*
   * local登录必填项
   */

  /** 手机号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_LOCAL)
  private String phoneNum;

  /** 密码 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_LOCAL)
  private String pwd;

  /** 个推id */
  private String registrationId;


  /** salt 注册时系统自动生成掩码 */
  // private String salt = RandomNumberUtil.getRandomString(4, 6);

  public NewLoginRequest(UserBaseInfo info) {
    super(info);
  }

  public abstract String getMoudelInfo();

  public void setRegistUserInfo(UserModel model) {
    if (null == model) model = new UserModel();
    model.setPlatform(platform);
    model.setModule(getModule());
    switch (platform) {
      case UcenterModelConstant.PLATFORM_LOCAL:
        break;
      case UcenterModelConstant.PLATFORM_DEVICE:
        setDeviceRegistUserInfo(model);
        break;
      case UcenterModelConstant.PLATFORM_QQ:
      case UcenterModelConstant.PLATFORM_WEIBO:
      case UcenterModelConstant.PLATFORM_WEIXIN:
        setThirdRegistUserInfo(model);
        break;
      default:
        break;
    }
  }

  private void setThirdRegistUserInfo(UserModel model) {
    model.setNickName(StringUtils.isNotBlank(getNickName()) ? getNickName() : "小逗" + randCode(5)); // 设置昵称
    model.setPortrait(getPortrait()); // 设置头像
    model.setAge(getAge()); // 设置年龄
    model.setAddress(getAddress()); // 设置地址
    model.setGender((null != getGender() ? getGender() : Gender.MAN.getCode())); // 设置性别
    model.setCreateTime(getCreateTime()); // 设置创建时间
    model.setToken(getToken()); // 设置令牌
    model.setTokenTime(getTokenTime()); // 设置令牌创建时间
    model.setUsedDeviceId(getDeviceId()); // 设置设备号
    model.setLatestDeviceIp(getClientIp()); // 设置当前IP
    model.setPlatform(getPlatform());// 设置所属平台
    model.setUniqueId(getPlatform() + getUniqueId());// 设置第三方平台唯一标识符
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getPlatform() + getUniqueId());// 暂且先放这个值
    model.setModuleInfo(getMoudelInfo()); // 设置模块信息
  }

  private void setDeviceRegistUserInfo(UserModel model) {
    model.setCreateTime(getCreateTime()); // 设置创建时间
    model.setToken(getToken()); // 设置令牌
    model.setTokenTime(getTokenTime()); // 设置令牌创建时间
    model.setGender((null != getGender() ? getGender() : Gender.MAN.getCode())); // 设置性别
    model.setPortrait(StringUtils.isNotBlank(getPortrait())
        ? getPortrait()
        : UcenterModelConstant.PORTRAIT); // 设置头像
    model.setUsedDeviceId(getDeviceId()); // 设置设备号
    model.setLatestDeviceIp(getClientIp()); // 设置当前IP
    model.setPlatform(getPlatform());// 设置所属平台
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getDeviceId());// 数据表中的唯一项，不能重复，所以得有值
    model.setModuleInfo(getMoudelInfo()); // 设置模块信息
  }

  public Timestamp getTokenTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  // public String getSalt() {
  // return salt;
  // }

  public Timestamp getCreateTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  public String getToken() {
    return UUID.randomUUID().toString();
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }


  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }

  public static String randCode(int n) {
    char[] arrChar =
        new char[] {'a', 'b', 'd', 'c', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'r', 'q',
            's', 't', 'u', 'v', 'w', 'z', 'y', 'x', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'Q', 'P', 'R',
            'S', 'V', 'U', 'W', 'X', 'Y', 'Z'};
    StringBuilder num = new StringBuilder();
    Random rnd = new Random();
    for (int i = 0; i < n; i++) {
      num.append(String.valueOf(arrChar[rnd.nextInt(arrChar.length - 1)]));
    }
    return num.toString();
  }

  public LoginRequest getLoginRequest() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setClientIp(getClientIp());
    loginRequest.setClientType(getClientType());
    loginRequest.setDeviceId(getDeviceId());
    loginRequest.setModule(getModule());
    loginRequest.setPhoneNum(getPhoneNum());
    loginRequest.setPwd(getPwd());
    loginRequest.setRegistrationId(getRegistrationId());
    loginRequest.setSessionToken(getSessionToken());
    loginRequest.setVersion(getVersion());
    return loginRequest;
  }

  public boolean canRegist() {
    return StringUtils.isNotBlank(platform)
        && !platform.equals(UcenterModelConstant.PLATFORM_LOCAL);
  }

  public UserModel getQueryModel() {
    UserModel model = new UserModel();
    model.setPlatform(platform);
    model.setModule(getModule());
    switch (platform) {
      case UcenterModelConstant.PLATFORM_LOCAL:
        model.setUserName(phoneNum);
        break;
      case UcenterModelConstant.PLATFORM_DEVICE:
        model.setUserName(getDeviceId());
        break;
      case UcenterModelConstant.PLATFORM_QQ:
      case UcenterModelConstant.PLATFORM_WEIBO:
      case UcenterModelConstant.PLATFORM_WEIXIN:
        String newUniqueId = platform + uniqueId;// 以保证每个平台之间没有相同的uniqueId
        model.setUniqueId(newUniqueId);
        break;
      default:
        break;
    }
    return model;
  }

  public boolean checkPassword(UserModel model) {
    if (UcenterModelConstant.PLATFORM_LOCAL.equals(platform)) {
      String passwordFromApp = UcenterUtil.getPasswd(pwd + model.getSalt());
      return model.getPassword().equals(passwordFromApp);
    }
    return true;
  }

}
