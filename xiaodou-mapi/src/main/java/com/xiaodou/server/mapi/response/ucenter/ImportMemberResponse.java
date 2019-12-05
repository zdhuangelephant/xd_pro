package com.xiaodou.server.mapi.response.ucenter;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ImportMemberResponse extends BaseResponse {

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
