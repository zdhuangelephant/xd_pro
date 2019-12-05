package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class ModifyPwdResponse extends BaseResponse {
  public ModifyPwdResponse() {}

  public ModifyPwdResponse(UcenterResType type) {
    super(type);
  }

  public ModifyPwdResponse(ResultType type) {
    super(type);
  }
}
