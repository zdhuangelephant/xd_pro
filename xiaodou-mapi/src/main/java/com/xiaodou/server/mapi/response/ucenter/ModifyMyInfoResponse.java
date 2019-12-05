package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ModifyMyInfoResponse extends BaseResponse {

  public ModifyMyInfoResponse() {}

  public ModifyMyInfoResponse(ResultType type) {
    super(type);
  }

  public ModifyMyInfoResponse(UcenterResType resType) {
    super(resType);
  }

}
