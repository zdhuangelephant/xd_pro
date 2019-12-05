package com.xiaodou.im.request;

/**
 * @name @see com.xiaodou.im.request.GetUserStatus.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月1日
 * @description 获取用户状态
 * @version 1.0
 */
public class GetStatusPojo extends BasePojo {

  /** targetUserId 目标用户ID */
  private String targetUserId;

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

}
