package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class QueryUserByNameRequest extends MapiBaseRequest {
  @NotEmpty
  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
