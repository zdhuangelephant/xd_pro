package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserLearnExamDateRequest extends BaseRequest {

  @NotEmpty
  private String courseId;
  /* page 页数 */
  @NotEmpty
  private String page;
  @NotEmpty
  private String firstLoginTime;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getFirstLoginTime() {
    return firstLoginTime;
  }

  public void setFirstLoginTime(String firstLoginTime) {
    this.firstLoginTime = firstLoginTime;
  }

}
