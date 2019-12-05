package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.ucerCenter.agent.enums.CheckTokenEnum;

public class CheckTokenResult extends ResultInfo {

  private String user;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

}
