package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class QueryUserListResponse extends BaseResponse {

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
