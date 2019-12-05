package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.quesbk.ExamDetailResponse.Question;

public class ExamHisDetailResponse extends BaseResponse {

  @Override
  public String toString0() {
    return String.format("[examId:%s,paperId:%s,examTime:%s,examType:%s]", examId, paperId,
        examTime, examTime);
  }

  public ExamHisDetailResponse(ResultType type) {
    super(type);
  }

  /**
   * 练习ID
   */
  private String examId;
  /**
   * 试卷ID
   */
  private String paperId;
  /**
   * 练习时间
   */
  private String examTime;
  /**
   * 练习类型
   */
  private String examType;
  /**
   * 练习详情
   */
  private List<ExamQuestion> examDetail;

  public String getExamId() {
    return examId;
  }

  public void setExamId(String examId) {
    this.examId = examId;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public List<ExamQuestion> getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(List<ExamQuestion> examDetail) {
    this.examDetail = examDetail;
  }

  public static class ExamQuestion extends Question {

    public ExamQuestion() {}

    public ExamQuestion(QuesbkQues quesbkQues) {
      super(quesbkQues);
    }

  }

}
