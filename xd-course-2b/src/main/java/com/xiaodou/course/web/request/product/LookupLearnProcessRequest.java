package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class LookupLearnProcessRequest  extends BaseRequest {

  /*课程id*/
  @NotEmpty
  private Integer courseId;
}