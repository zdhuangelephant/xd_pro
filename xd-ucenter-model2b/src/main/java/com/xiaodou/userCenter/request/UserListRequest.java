package com.xiaodou.userCenter.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserListRequest extends BaseRequest {

  /** userIdList 待查询用户ID列表 */
  @NotEmpty
  private List<String> userIdList = Lists.newArrayList();

  public List<String> getUserIdList() {
    return userIdList;
  }

  public void setUserIdList(List<String> userIdList) {
    this.userIdList = userIdList;
  }

}
