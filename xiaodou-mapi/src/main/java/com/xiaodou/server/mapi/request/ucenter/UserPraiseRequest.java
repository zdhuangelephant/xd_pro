package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserPraiseRequest extends MapiBaseRequest {

  /**
   * 目标用户ID 
   */
  @NotEmpty
  private String targetUserId;

  public UserPraiseRequest() {}

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

}
