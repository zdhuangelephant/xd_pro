package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class FeedBackResponse extends BaseResponse {
  
  public FeedBackResponse(){}

  public FeedBackResponse(ResultType type) {
    super(type);
  }

  public FeedBackResponse(UcenterResType resType) {
    super(resType);
  }

}
