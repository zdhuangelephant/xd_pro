package com.xiaodou.resources.model.quesbk;

import java.util.Date;

import com.xiaodou.resources.model.BaseEntity;

public class User extends BaseEntity {
  private Integer id;

  private String userName;

  private String nickName;

  private String salt;

  private String password;

  private String portrait;

  private Date tokenTime;

  private String token;

  private String address;

  private Integer age;

  private Date createTime;

  private Date timestamp;

  private String usedDeviceId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName == null ? null : userName.trim();
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName == null ? null : nickName.trim();
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt == null ? null : salt.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password == null ? null : password.trim();
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait == null ? null : portrait.trim();
  }

  public Date getTokenTime() {
    return tokenTime;
  }

  public void setTokenTime(Date tokenTime) {
    this.tokenTime = tokenTime;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token == null ? null : token.trim();
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address == null ? null : address.trim();
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getUsedDeviceId() {
    return usedDeviceId;
  }

  public void setUsedDeviceId(String usedDeviceId) {
    this.usedDeviceId = usedDeviceId == null ? null : usedDeviceId.trim();
  }
}
