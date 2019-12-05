package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 课程详情request
 * 
 * @author bing.cheng
 * 
 */
public class ProductDetailRequest extends BaseRequest {

  /** 产品id */
  @NotEmpty
  private Integer courseId;

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }
}
