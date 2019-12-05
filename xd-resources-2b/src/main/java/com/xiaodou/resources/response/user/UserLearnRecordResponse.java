package com.xiaodou.resources.response.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.model.user.UserLearnRecordDayModel;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnRecordResponse extends BaseResponse {

  public UserLearnRecordResponse(ResultType resultType) {
    super(resultType);
  }

  public UserLearnRecordResponse(ProductResType resultType) {
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
