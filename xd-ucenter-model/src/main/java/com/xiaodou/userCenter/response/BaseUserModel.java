package com.xiaodou.userCenter.response;

import com.alibaba.fastjson.JSON;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.vo.UserModelVo;

public abstract class BaseUserModel implements UserModelResponse {

  /**
   * 主键id
   */
  private String id;

  /**
   * 所属模块
   */
  private String module;

  /**
   * 盐值
   */
  private String salt;

  /**
   * token初始时间
   */
  private String tokenTime;

  /**
   * token值
   */
  private String token;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 用过的设备号列表,设备号之间以逗号分隔
   */
  private String usedDeviceId;

  /**
   * 用户年龄
   */
  private String age;

  /**
   * 用户地址
   */
  private String address;

  /**
   * 最新device ip
   */
  private String latestDeviceIp;

  /**
   * 用户名(手机号)
   */
  private String userName;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 性别
   */
  private String gender;

  /**
   * 头像URL地址
   */
  private String portrait;
  // local/qq/weibo/weixin;所属平台
  private String platform;

  // 第三方唯一标识
  private String uniqueId;

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTokenTime() {
    return tokenTime;
  }

  public void setTokenTime(String tokenTime) {
    this.tokenTime = tokenTime;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
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
    this.nickName = nickName;
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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsedDeviceId() {
    return usedDeviceId;
  }

  public void setUsedDeviceId(String usedDeviceId) {
    this.usedDeviceId = usedDeviceId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  @Override
  public UserModelResponse initFromUserModel(UserModel model) {
    if (null != model) {
      if (null != model.getCreateTime()) setCreateTime(model.getCreateTime().toString());
      if (null != model.getId()) setId(model.getId().toString());
      setLatestDeviceIp(model.getLatestDeviceIp());
      setSalt(model.getSalt());
      setToken(model.getToken());
      if (null != model.getTokenTime()) setTokenTime(model.getTokenTime().toString());
      setUsedDeviceId(model.getUsedDeviceId());
      setUserName(model.getUserName());
      setAddress(model.getAddress());
      if (null != model.getAge()) setAge(model.getAge().toString());
      setGender(Gender.getByCode(model.getGender().toString()).getName());
      setNickName(model.getNickName());
      setPortrait(model.getPortrait());
      setUniqueId(model.getUniqueId());
      setPlatform(model.getPlatform());
    }
    return this;
  }

  @Override
  public UserModelResponse initFromUserModelVo(UserModelVo model) {
    if (null != model) {
      if (null != model.getCreateTime()) setCreateTime(model.getCreateTime().toString());
      if (null != model.getId()) setId(model.getId().toString());
      if (null != model.getModule()) setModule(model.getModule());
      setLatestDeviceIp(model.getLatestDeviceIp());
      setSalt(model.getSalt());
      setToken(model.getToken());
      if (null != model.getTokenTime()) setTokenTime(model.getTokenTime().toString());
      setUsedDeviceId(model.getUsedDeviceId());
      setUserName(model.getUserName());
      setAddress(model.getAddress());
      if (null != model.getAge()) setAge(model.getAge().toString());
      setGender(Gender.getByCode(model.getGender().toString()).getName());
      setNickName(model.getNickName());
      setPortrait(model.getPortrait());
      setUniqueId(model.getUniqueId());
      setPlatform(model.getPlatform());
    }
    initModuleInfo(model);
    return this;
  }

  protected abstract void initModuleInfo(UserModelVo model);

}
