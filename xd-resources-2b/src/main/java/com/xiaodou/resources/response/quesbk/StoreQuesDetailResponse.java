package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.quesbk.ExamDetailResponse.Question;

public class StoreQuesDetailResponse extends BaseResponse {
  @Override
  public String toString0() {
    return FastJsonUtil.toJson(Lists.transform(questionList, new Function<StoreQuestion, String>() {
      @Override
      public String apply(StoreQuestion input) {
        return String.format("quesId:%s", input.getQuesId());
      }
    }));
  }

  public StoreQuesDetailResponse(ResultType type) {
    super(type);
  }

  private List<StoreQuestion> questionList = Lists.newArrayList();


  public List<StoreQuestion> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<StoreQuestion> questionList) {
    this.questionList = questionList;
  }


  public static class StoreQuestion extends Question {

    public StoreQuestion() {}

    public StoreQuestion(QuesbkQues quesbkQues) {
      super(quesbkQues);
     /* // 查询收藏问题默认就是被收藏的
      setStoreStatus("1");*/
    }
  }

}
