package com.xiaodou.ucenter.request;

import java.sql.Timestamp;
import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.ucenter.constant.UcenterConstant;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.module.ModuleWrapper;
import com.xiaodou.ucenter.util.RandomNumberUtil;
import com.xiaodou.ucenter.util.UcenterUtil;

public class RegistUserRequest extends BaseUserInfo {

  /**
   * 手机号
   */
  @NotEmpty
  private String phoneNum;

  /**
   * 密码
   */
  @NotEmpty
  private String passWord;

  /**
   * 验证码
   */
  @NotEmpty
  private String checkCode;

  /** salt 注册时系统自动生成掩码 */
  private String salt = RandomNumberUtil.getRandomString(4, 6);

  public RegistUserRequest(BaseUserInfo info) {
    super(info);
  }

  public RegistUserRequest() {}

  public void setRegistUserInfo(UserModel model) {
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getPhoneNum()); // 设置用户名
    model.setSalt(getSalt()); // 设置掩码
    model.setNickName(StringUtils.isNotBlank(getNickName()) ? getNickName() : "小逗"
        + getPhoneNum().substring(6) + System.currentTimeMillis()); // 设置昵称
    model.setPortrait(StringUtils.isNotBlank(getPortrait())
        ? getPortrait()
        : UcenterConstant.PORTRAIT); // 设置头像
    model.setAge(getAge()); // 设置年龄
    model.setAddress(getAddress()); // 设置地址
    model.setGender(null != getGender() ? getGender() : Gender.MAN.getCode()); // 设置性别
    model.setPassword(getPasswd()); // 设置密码
    model.setCreateTime(getCreateTime()); // 设置创建时间
    model.setToken(getToken()); // 设置令牌
    model.setTokenTime(getTokenTime()); // 设置令牌创建时间
    model.setDeviceId(getDeviceId()); // 设置设备号
    model.setLatestDeviceIp(getClientIp()); // 设置当前IP
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getPhoneNum()); // 设置用户名
    model.setPlatform(UcenterConstant.PLATFORM_LOCAL);
    model.setModule(ModuleWrapper.getWrapper().getModule()); // 设置模块号
  }

  public Timestamp getTokenTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  public String getPasswd() {
    return UcenterUtil.getPasswd(getPassWord() + getSalt());
  }

  public String getSalt() {
    return salt;
  }

  public Timestamp getCreateTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  public String getToken() {
    return UUID.randomUUID().toString();
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

}
