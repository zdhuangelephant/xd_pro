package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.behavior.UserExamRecord.ExamDate;

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
