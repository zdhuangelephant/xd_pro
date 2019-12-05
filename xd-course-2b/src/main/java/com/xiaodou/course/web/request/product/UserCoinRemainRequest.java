package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserCoinRemainRequest extends BaseRequest {
  // 课程id
  @NotEmpty
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }
}
