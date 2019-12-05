package com.xiaodou.userCenter.response;

import com.xiaodou.userCenter.model.UserModel;

public interface UserModelResponse {

  public abstract UserModelResponse initFromUserModel(UserModel model);
}
