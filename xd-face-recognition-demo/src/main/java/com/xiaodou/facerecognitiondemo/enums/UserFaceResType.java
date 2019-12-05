package com.xiaodou.facerecognitiondemo.enums;

public enum UserFaceResType {

  CreateUserFail("40101", "创建用户失败，无法识别图片用户"), CompareUserFail("40102", "用户比对失败，无法识别图片用户");
  UserFaceResType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private String code;
  private String desc;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
