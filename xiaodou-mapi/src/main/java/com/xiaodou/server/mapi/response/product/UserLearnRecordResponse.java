package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.UserLearnRecordDayModel;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnRecordResponse extends BaseResponse {

  public UserLearnRecordResponse() {}

  public UserLearnRecordResponse(ResultType resultType) {
    super(resultType);
  }

  private List<UserLearnRecordDayModel> learnList = Lists.newArrayList();

  public List<UserLearnRecordDayModel> getLearnList() {
    return learnList;
  }

  public void setLearnList(List<UserLearnRecordDayModel> learnList) {
    this.learnList = learnList;
  }
}
