package com.xiaodou.course.web.response.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.model.user.UserLearnRecordDayModel.LearnRecordData;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
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
