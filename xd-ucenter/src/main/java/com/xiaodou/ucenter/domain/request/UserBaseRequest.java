package com.xiaodou.ucenter.domain.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class UserBaseRequest extends BaseValidatorPojo {

  @NotEmpty
  private String xdUniqueId;

  public String getXdUniqueId() {
    return xdUniqueId;
  }

  public void setXdUniqueId(String xdUniqueId) {
    this.xdUniqueId = xdUniqueId;
  }

}
