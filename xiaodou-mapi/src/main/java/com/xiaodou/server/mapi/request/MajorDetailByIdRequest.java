package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;


public class MajorDetailByIdRequest extends MapiBaseRequest {

  /* 专业id */
  @NotEmpty
  private String majorId;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

}
