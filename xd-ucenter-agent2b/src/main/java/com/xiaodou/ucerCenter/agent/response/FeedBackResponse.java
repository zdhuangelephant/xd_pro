package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class FeedBackResponse extends BaseResultInfo {

  public FeedBackResponse() {}

  public FeedBackResponse(ResultType type) {
    super(type);
  }

  public FeedBackResponse(UcenterResType resType) {
    super(resType);
  }

}
