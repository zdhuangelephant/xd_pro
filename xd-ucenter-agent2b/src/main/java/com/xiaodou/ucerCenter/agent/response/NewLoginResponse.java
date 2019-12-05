package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;

public class NewLoginResponse extends LoginResponse {

  public NewLoginResponse() {}

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
