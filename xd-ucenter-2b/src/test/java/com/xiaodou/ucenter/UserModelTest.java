package com.xiaodou.ucenter;

public class UserModelTest {
  private String nickName;
  private String gender;
  private String portrait;

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    if ("男".equals(gender))
      this.gender = "1";
    else if ("女".equals(gender))
      this.gender = "2";
    else
      this.gender = "0";
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }
}
