package com.xiaodou.autopractise.enums;

public enum UserCourseStatus {
  EXAMING(1, "练习中"), ERROR(-2, "异常"), FINISH(-1, "已完成"), UNUSE(-3, "本课程作业提交还没有开始或已过期"), ALLBLOCK(
      -4, "等待试卷答案");
  UserCourseStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private Integer code;
  private String desc;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public static UserCourseStatus getStatusByCode(Integer code) {
    if (null == code) {
      return EXAMING;
    }
    if (EXAMING.code == code) {
      return EXAMING;
    }
    if (ERROR.code == code) {
      return ERROR;
    }
    if (FINISH.code == code) {
      return FINISH;
    }
    if (UNUSE.code == code) {
      return UNUSE;
    }
    if (ALLBLOCK.code == code) {
      return ALLBLOCK;
    }
    return EXAMING;
  }
}
