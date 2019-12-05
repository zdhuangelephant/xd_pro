package com.xiaodou.server.mapi.request.product;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserLearnScoreDateRequest extends MapiBaseRequest {

  @NotEmpty
  private String courseId;

  /* page 页数 */
  @NotEmpty
  private String page;

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

}
