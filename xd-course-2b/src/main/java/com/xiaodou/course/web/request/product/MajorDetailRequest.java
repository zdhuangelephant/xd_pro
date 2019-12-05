package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"typeCode"})
public class MajorDetailRequest extends BaseRequest {

  /* 专业Id */
  private String majorId;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }
}
