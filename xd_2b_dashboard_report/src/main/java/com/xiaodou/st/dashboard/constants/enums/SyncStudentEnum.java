package com.xiaodou.st.dashboard.constants.enums;

public enum SyncStudentEnum {

  STUDENT("1", "同步用户"), APPLY("2", "同步课程"),ADMISSIONCARDCODE("3","同步准考证");

  private String code;
  private String name;

  private SyncStudentEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
