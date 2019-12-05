package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.UserExamRecord.ExamDate;
import com.xiaodou.resources.response.BaseResponse;

public class UserLearnExamDateResponse extends BaseResponse {

  public UserLearnExamDateResponse(ResultType type) {
    super(type);
  }

  private List<ExamDate> examDateList = Lists.newArrayList();

  public List<ExamDate> getExamDateList() {
    return examDateList;
  }

  public void setExamDateList(List<ExamDate> examDateList) {
    this.examDateList = examDateList;
  }

}
