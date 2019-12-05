package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 课程详情request
 */
public class ProductDetailRequest extends MapiBaseRequest {

  /** 产品(课程)Id */
  @NotEmpty
  private Integer courseId;

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

}
