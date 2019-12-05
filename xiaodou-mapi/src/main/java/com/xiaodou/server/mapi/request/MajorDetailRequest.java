package com.xiaodou.server.mapi.request;


public class MajorDetailRequest extends MapiBaseRequest {

  /* 专业id */
  private String majorId;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

}
