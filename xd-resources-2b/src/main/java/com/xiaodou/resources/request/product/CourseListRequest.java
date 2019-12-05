package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;

public class CourseListRequest extends BaseRequest {

  private String courseName;

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

}
