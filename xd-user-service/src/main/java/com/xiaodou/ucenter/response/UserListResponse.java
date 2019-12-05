package com.xiaodou.ucenter.response;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class UserListResponse extends BaseResponse {

  private List<Map<String, Object>> userModelList = Lists.newArrayList();

  public UserListResponse() {}

  public UserListResponse(ResultType type) {
    super(type);
  }

  public UserListResponse(UcenterResType type) {
    super(type);
  }

  public List<Map<String, Object>> getUserModelList() {
    return userModelList;
  }

  public void setUserModelList(List<Map<String, Object>> userModelList) {
    this.userModelList = userModelList;
  }

}
