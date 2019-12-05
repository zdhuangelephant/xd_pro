package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class UserProductOpenRequest extends BaseValidatorPojo {
  // 课程id
  @NotEmpty
  private String courseId;
  // 购买状态(1:正常购买状态，2：试用状态)
  @NotEmpty
  private String orderStatus;
  @NotEmpty
  private String uid;

  @NotEmpty
  private String module;

  @NotEmpty
  private String typeCode;


  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }
}
