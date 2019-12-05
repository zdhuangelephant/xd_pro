package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class ModifyMyInfoResponse extends BaseResultInfo {

  public ModifyMyInfoResponse(ResultType type) {
    super(type);
  }

  public ModifyMyInfoResponse(UcenterResType resType) {
    super(resType);
  }

}
