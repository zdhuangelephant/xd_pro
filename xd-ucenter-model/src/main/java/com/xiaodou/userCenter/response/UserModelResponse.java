package com.xiaodou.userCenter.response;

import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.vo.UserModelVo;

public interface UserModelResponse {

  public abstract UserModelResponse initFromUserModelVo(UserModelVo model);

  public abstract UserModelResponse initFromUserModel(UserModel model);
}
