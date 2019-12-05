package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class ModifyMyInfoResponse extends BaseResultInfo {

  public ModifyMyInfoResponse() {}

  public ModifyMyInfoResponse(ResultType type) {
    super(type);
  }

  public ModifyMyInfoResponse(UcenterResType resType) {
    super(resType);
  }

}
