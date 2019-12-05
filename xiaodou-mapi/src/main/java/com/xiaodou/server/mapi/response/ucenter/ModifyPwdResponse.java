package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ModifyPwdResponse extends BaseResponse {

  public ModifyPwdResponse() {}

  public ModifyPwdResponse(UcenterResType type) {
    super(type);
  }

  public ModifyPwdResponse(ResultType type) {
    super(type);
  }
}
