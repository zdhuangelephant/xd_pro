package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class ModifyUserResponse extends BaseResponse {

  public ModifyUserResponse() {}

  public ModifyUserResponse(ResultType type) {
    super(type);
  }

  public ModifyUserResponse(UcenterResType resType) {
    super(resType);
  }

}
