package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NewestCourseRequest extends BaseRequest {

  @NotEmpty
  private String courseId;
  
  private String sinceId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getSinceId() {
    return sinceId;
  }

  public void setSinceId(String sinceId) {
    this.sinceId = sinceId;
  }


}
