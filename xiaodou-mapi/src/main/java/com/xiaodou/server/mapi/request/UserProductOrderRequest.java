package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserProductOrderRequest extends MapiBaseRequest {
  // 学习时间
  @NotEmpty
  private String courseId;
  // 购买状态(1:正常购买状态，2：试用状态)
  @NotEmpty
  private String orderStatus;

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
}
