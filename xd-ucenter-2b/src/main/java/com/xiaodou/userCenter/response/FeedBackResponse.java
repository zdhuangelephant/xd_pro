package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class FeedBackResponse extends BaseResultInfo {
  public FeedBackResponse() {
  }

  public FeedBackResponse(ResultType type) {
    super(type);
  }

  public FeedBackResponse(UcenterResType resType) {
    super(resType);
  }

}
