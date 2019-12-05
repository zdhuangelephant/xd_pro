package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class ImportMemberResponse extends BaseResultInfo {

  public ImportMemberResponse() {}

  public ImportMemberResponse(ResultType type) {
    super(type);
  }

  public ImportMemberResponse(UcenterResType resType) {
    super(resType);
  }

  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
