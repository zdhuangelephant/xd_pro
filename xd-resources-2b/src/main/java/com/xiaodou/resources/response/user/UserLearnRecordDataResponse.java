package com.xiaodou.resources.response.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.model.user.UserLearnRecordDayModel.LearnRecordData;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnRecordDataResponse extends BaseResponse {

  public UserLearnRecordDataResponse(ResultType resultType) {
    super(resultType);
  }

  public UserLearnRecordDataResponse(ProductResType resultType) {
    super(resultType);
  }

  private List<LearnRecordData> learnList = Lists.newArrayList();

  public List<LearnRecordData> getLearnList() {
    return learnList;
  }

  public void setLearnList(List<LearnRecordData> learnList) {
    this.learnList = learnList;
  }
}
