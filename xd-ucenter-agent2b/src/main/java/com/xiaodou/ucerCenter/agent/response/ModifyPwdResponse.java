package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class ModifyPwdResponse extends BaseResultInfo {

  public ModifyPwdResponse() {}

  public ModifyPwdResponse(UcenterResType type) {
    super(type);
  }

  public ModifyPwdResponse(ResultType type) {
    super(type);
  }
}
