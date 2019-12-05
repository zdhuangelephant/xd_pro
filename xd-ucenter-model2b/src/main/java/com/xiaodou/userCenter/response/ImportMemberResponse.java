package com.xiaodou.userCenter.response;

import org.apache.commons.lang.StringUtils;

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

  private String userId = StringUtils.EMPTY;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
