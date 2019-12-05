package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserListResponse extends BaseResponse {

  public UserListResponse() {}

  public UserListResponse(ResultType type) {
    super(type);
  }
  private List<Map<String, Object>> userModelList = Lists.newArrayList();

  public List<Map<String, Object>> getUserModelList() {
    return userModelList;
  }

  public void setUserModelList(List<Map<String, Object>> userModelList) {
    this.userModelList = userModelList;
  }

}
