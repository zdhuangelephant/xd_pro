package com.xiaodou.ucenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserListRequest extends BaseRequest {

  /** userIdList 待查询用户ID列表 */
  @NotEmpty
  private String userIdList;

  public String getUserIdList() {
    return userIdList;
  }

  public void setUserIdList(String userIdList) {
    this.userIdList = userIdList;
  }
}
