package com.xiaodou.resources.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class StatisticsSharePvRequest extends BaseValidatorPojo {
  @NotEmpty
  private String resourceId;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }
}
