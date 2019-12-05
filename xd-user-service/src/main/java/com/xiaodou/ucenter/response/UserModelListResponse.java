package com.xiaodou.ucenter.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class UserModelListResponse extends BaseResponse {

  public UserModelListResponse() {}

  public UserModelListResponse(ResultType type) {
    super(type);
  }

  public UserModelListResponse(UcenterResType resType) {
    super(resType);
  }

  private List<UserModel> userModelList = Lists.newArrayList();

  public List<UserModel> getUserModelList() {
    return userModelList;
  }

  public void setUserModelList(List<UserModel> userModelList) {
    this.userModelList = userModelList;
  }

}
