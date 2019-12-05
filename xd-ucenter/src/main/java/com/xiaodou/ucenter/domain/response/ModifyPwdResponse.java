package com.xiaodou.ucenter.domain.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;

public class ModifyPwdResponse extends BaseResultInfo {
  public ModifyPwdResponse(UcenterResType type) {
    super(type);
  }

  public ModifyPwdResponse(ResultType type) {
    super(type);
  }
}
