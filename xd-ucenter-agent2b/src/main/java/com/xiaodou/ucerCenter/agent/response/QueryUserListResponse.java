package com.xiaodou.ucerCenter.agent.response;

import java.util.List;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class QueryUserListResponse extends BaseResultInfo {

  private List<UserModelResponse> userList;

  public QueryUserListResponse() {}

  public QueryUserListResponse(ResultType type) {
    super(type);
  }

  public QueryUserListResponse(UcenterResType type) {
    super(type);
  }

  public List<UserModelResponse> getUserList() {
    return userList;
  }

  public void setUserList(List<UserModelResponse> userList) {
    this.userList = userList;
  }
}
