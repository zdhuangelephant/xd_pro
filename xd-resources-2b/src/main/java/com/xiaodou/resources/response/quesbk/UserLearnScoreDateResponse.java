package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.UserExamRecord.ScoreDate;
import com.xiaodou.resources.response.BaseResponse;

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
