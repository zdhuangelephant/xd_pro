package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.behavior.UserExamRecord.ScoreDate;

public class UserLearnScoreDateResponse extends BaseResponse {

  public UserLearnScoreDateResponse(ResultType type) {
    super(type);
  }

  private List<ScoreDate> scoreDateList = Lists.newArrayList();

  public List<ScoreDate> getScoreDateList() {
    return scoreDateList;
  }

  public void setScoreDateList(List<ScoreDate> scoreDateList) {
    this.scoreDateList = scoreDateList;
  }

}
