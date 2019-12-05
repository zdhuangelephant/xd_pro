package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.userCenter.request.FriendAddRequest.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月1日
 * @description 添加好友请求
 * @version 1.0
 */
public class FriendAddRequest extends BaseRequest {

  /** targetUserId 目标用户ID */
  @NotEmpty
  private String targetUserId;

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

}
