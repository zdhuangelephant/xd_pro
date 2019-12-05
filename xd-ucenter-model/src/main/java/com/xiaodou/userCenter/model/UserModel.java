package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.userCenter.model.vo.UserModelVo;

public class UserModel {
  /**
   * 主键id
   */
  @GeneralField
  @BaseField
  private Long id;

//  /**
//   * 所属模块
//   */
//  @BaseField
//  private String module;

  /**
   * 盐值
   */
  @BaseField
  private String salt;

  /**
   * token初始时间
   */
  @BaseField
  private Timestamp tokenTime;

  /**
   * token值
   */
  @GeneralField
  @BaseField
  private String token;

  /**
   * 创建时间
   */
  @BaseField
  private Timestamp createTime;

  /**
   * 用过的设备号列表,设备号之间以逗号分隔
   */
  @BaseField
  private String usedDeviceId;

  /**
   * 用户年龄
   */
  @BaseField
  private Integer age;

  /**
   * 用户地址
   */
  @BaseField
  private String address;

  /**
   * 最新device ip
   */
  @BaseField
  private String latestDeviceIp;

  /**
   * 用户名(手机号)
   */
  @GeneralField
  @BaseField
  private String userName;

  /**
   * 昵称
   */
  @BaseField
  private String nickName;

  /**
   * 性别
   */
  @BaseField
  private Integer gender;

  /**
   * 头像URL地址
   */
  @BaseField
  private String portrait;

  /**
   * 用户密码
   */
  private String password;

//  /**
//   * 所属模块信息
//   */
//  @BaseField
//  private String moduleInfo;

  // 第三方登录接口属性 START ADD LIDEHONG
  // local/qq/weibo/weixin;所属平台
  @BaseField
  private String platform;

  // 第三方唯一标识
  @BaseField
  private String uniqueId;

  // 第三方登录接口属性 END ADD LIDEHONG

  public Integer getGenderCode() {
    return gender;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = "".equals(gender) ? null : gender;
  }

  public void setGender(String gender) {
    if (StringUtils.isBlank(gender)) return;
    this.gender = Gender.getByCode(gender).getCode();
  }

  public String getLatestDeviceIp() {
    return latestDeviceIp;
  }

  public void setLatestDeviceIp(String latestDeviceIp) {
    this.latestDeviceIp = latestDeviceIp;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = ("".equals(nickName)) ? null : nickName;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public Timestamp getTokenTime() {
    return tokenTime;
  }

  public void setTokenTime(Timestamp tokenTime) {
    this.tokenTime = tokenTime;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getUsedDeviceId() {
    return usedDeviceId;
  }

  public void setUsedDeviceId(String usedDeviceId) {
    this.usedDeviceId = usedDeviceId;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = "".equals(address) ? null : address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
  
  public UserModelVo getUserModelVo(UserModuleInfoModel moduleInfoModel){
		 UserModelVo vo = new UserModelVo();
		 vo.setAddress(address);
		 vo.setAge(age);
		 vo.setCreateTime(createTime);
		 vo.setGender(gender);
		 vo.setGender(gender);
		 vo.setId(id);
		 vo.setLatestDeviceIp(latestDeviceIp);
		 vo.setNickName(nickName);
		 vo.setPassword(password);
		 vo.setPlatform(platform);
		 vo.setPortrait(portrait);
		 vo.setSalt(salt);
		 vo.setToken(token);
		 vo.setTokenTime(tokenTime);
		 vo.setUniqueId(uniqueId);
		 vo.setUsedDeviceId(usedDeviceId);
		 vo.setUserName(userName);
		 vo.setModule(moduleInfoModel.getModule());
		 vo.setModuleInfo(moduleInfoModel.getModuleInfo());
		 return vo;
	 }
}
