package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class InitAppRequest extends BaseRequest {
  /**
   * 个推id
   */
  @NotEmpty
  private String publishId;
  /**
   * 系统类型
   */
  @NotEmpty
  private String systemType;
  public String getPublishId() {
    return publishId;
  }

  public void setPublishId(String publishId) {
    this.publishId = publishId;
  }
  public String getSystemType() {
    return systemType;
  }

  public void setSystemType(String systemType) {
    this.systemType = systemType;
  }
}
