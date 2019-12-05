package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class NewLoginResponse extends LoginResponse {

  public NewLoginResponse(UcenterResType type) {
    super(type);
  }

  public NewLoginResponse(ResultType type) {
    super(type);
  }

  private UserModelResponse moduleInfo;

  public UserModelResponse getModuleInfo() {
    return moduleInfo;
  }

  public void setModuleInfo(UserModelResponse moduleInfo) {
    this.moduleInfo = moduleInfo;
  }
}
