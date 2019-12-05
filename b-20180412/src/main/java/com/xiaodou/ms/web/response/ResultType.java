package com.xiaodou.ms.web.response;

/**
 * Created by zyp on 15/4/19.
 */
public enum ResultType {

  SUCCESS(0,"成功"),
  SYS_FAIL(-1,"系统异常"),
  VALID_FAIL(-2,"参数校验错误"),
  DUPLICATE_CATEGORY(20101, "专业已存在"),
  DUPLICATE_MAJOR_COURSE(20102, "该课程已被该机器人挂载"),
  LACK_CHAPTER_ITEM_INVALID(20103, "该课程下无章节信息"),
  NO_REALTION_TO_DELETE(20104, "课程与专业没有关联"),
  PUSH_TAG_NULL_ERROR(20105, "TAG不能为空！");

  private Integer retCode;

  private String retDesc;

  ResultType(Integer retCode, String retDesc) {
    this.retCode = retCode;
    this.retDesc = retDesc;
  }

  public Integer getRetCode() {
    return retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }
}
