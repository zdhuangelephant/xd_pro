package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserProductOpenRequest extends BaseRequest{
   /*课程码值*/
  @NotEmpty
  private String courseCode;
  /* 购买状态(1:正常购买状态，2：试用状态) */
  @NotEmpty
  private String orderStatus;

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }
}
