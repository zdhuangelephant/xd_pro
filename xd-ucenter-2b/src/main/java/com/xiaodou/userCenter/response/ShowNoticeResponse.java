package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class ShowNoticeResponse extends BaseResultInfo {

  public ShowNoticeResponse() {}

  public ShowNoticeResponse(UcenterResType type) {
    super(type);
  }

  public ShowNoticeResponse(ResultType type) {
    super(type);
  }
  
  
}
