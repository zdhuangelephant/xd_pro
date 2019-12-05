package com.xiaodou.server.mapi.request;

import java.util.List;

import com.google.common.collect.Lists;

public class UserListRequest extends MapiBaseRequest {

  private List<String> userIdList = Lists.newArrayList();

  public List<String> getUserIdList() {
    return userIdList;
  }

  public void setUserIdList(List<String> userIdList) {
    this.userIdList = userIdList;
  }

}
