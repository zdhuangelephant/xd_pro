package com.xiaodou.userCenter.model;

public class BaseUserInfo {

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 性别
   */
  private Integer gender;

  /**
   * 头像URL地址
   */
  private String portrait;

  /**
   * 用户年龄
   */
  private Integer age;

  /**
   * 用户地址
   */
  private String address;

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
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
    this.address = address;
  }

}
