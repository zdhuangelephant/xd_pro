package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class LookupLearnProcessRequest  extends BaseRequest {

  /*课程id*/
  @NotEmpty
  private Integer courseId;
}