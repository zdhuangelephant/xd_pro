package com.xiaodou.server.mapi.request.ucenter;

import java.util.List;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class QueryUserListRequest extends MapiBaseRequest {
  /* userIdList 用戶id List */
  @NotEmpty
  private List<String> userIdList;

  public List<String> getUserIdList() {
    return userIdList;
  }

  public void setUserIdList(List<String> userIdList) {
    this.userIdList = userIdList;
  }

}
