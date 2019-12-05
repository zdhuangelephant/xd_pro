package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * 课程详情request
 * 
 * @author bing.cheng
 * 
 */
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"typeCode"})
public class ProductDetailRequest extends BaseRequest {

  /** 产品(课程)码Id */
  @NotEmpty
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
