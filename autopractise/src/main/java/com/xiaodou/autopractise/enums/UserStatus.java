package com.xiaodou.autopractise.enums;

/**
 * @name @see com.xiaodou.autopractise.enums.UserStatus.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月4日
 * @description 用户状态
 * @version 1.0
 */
public enum UserStatus {
  INIT(0, "初始化"), EXAMING(1, "练习中"), BLOCKING(2, "等待试卷答案"), FINISH(-1, "已完成"), WRONG(-2, "登录失败"), EMPTYCOURSE(
      -3, "课程列表为空");
  UserStatus(Integer code, String desc) {
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

  public static UserStatus getStatusByCode(Integer code) {
    if (null == code) {
      return INIT;
    }
    if (INIT.code == code) {
      return INIT;
    }
    if (EXAMING.code == code) {
      return EXAMING;
    }
    if (BLOCKING.code == code) {
      return BLOCKING;
    }
    if (FINISH.code == code) {
      return FINISH;
    }
    if (WRONG.code == code) {
      return WRONG;
    }
    if (EMPTYCOURSE.code == code) {
      return EMPTYCOURSE;
    }
    return INIT;
  }
}
