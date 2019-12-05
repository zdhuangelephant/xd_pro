package com.xiaodou.course.web.response.product;

import com.xiaodou.course.vo.product.ExamStatistic;
import com.xiaodou.course.vo.product.StudyStatistic;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/24.
 */
public class LearnStaticsResponse extends BaseResponse {

  // 学习统计
  private StudyStatistic studyStatistic;

  // 做题统计
  private ExamStatistic examStatistic;

  public ExamStatistic getExamStatistic() {
    return examStatistic;
  }

  public void setExamStatistic(ExamStatistic examStatistic) {
    this.examStatistic = examStatistic;
  }

  public StudyStatistic getStudyStatistic() {
    return studyStatistic;
  }

  public void setStudyStatistic(StudyStatistic studyStatistic) {
    this.studyStatistic = studyStatistic;
  }

  public LearnStaticsResponse(ResultType resultType) {
    super(resultType);
  }
}
