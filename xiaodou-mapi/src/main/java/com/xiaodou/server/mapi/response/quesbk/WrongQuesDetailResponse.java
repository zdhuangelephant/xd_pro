package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.response.quesbk.ExamDetailResponse.Question;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class WrongQuesDetailResponse extends BaseResponse {

  @Override
  public String toString0() {
    return FastJsonUtil.toJson(Lists.transform(questionList, new Function<WrongQuestion, String>() {
      @Override
      public String apply(WrongQuestion input) {
        return String.format("quesId:%s", input.getQuesId());
      }
    }));
  }

  public WrongQuesDetailResponse() {}

  public WrongQuesDetailResponse(ResultType type) {
    super(type);
  }

  private List<WrongQuestion> questionList = Lists.newArrayList();

  public List<WrongQuestion> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<WrongQuestion> questionList) {
    this.questionList = questionList;
  }


  public static class WrongQuestion extends Question {

    public WrongQuestion() {}

  }

}
