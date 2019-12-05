package com.xiaodou.ucenter.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.ucenter.constant.UcenterConstant;

public class BaseUserInfo extends BaseRequest {

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 头像
   */
  private String portrait;

  /**
   * 年龄
   */
  private Integer age;

  /**
   * 地址
   */
  private String address;
  /**
   * 性别
   */
  @LegalValueList({UcenterConstant.MAN, UcenterConstant.WOMAN})
  private Integer gender;

  private String sign; // 签名

  public BaseUserInfo() {}

  public BaseUserInfo(BaseUserInfo info) {
    if (null != info) {
      nickName = info.getNickName();
      portrait = info.getPortrait();
      age = info.getAge();
      address = info.getAddress();
      gender = info.getGender();
    }
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
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

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

}
