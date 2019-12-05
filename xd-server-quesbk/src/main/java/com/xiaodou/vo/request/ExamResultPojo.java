package com.xiaodou.vo.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ExamResultPojo extends QuesBasePojo {

  /** userCredit 用户积分 */
  @NotEmpty
  private Integer userCredit;
  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;
  /**
   * 试卷编号
   */
  @NotEmpty
  private String paperId;
  /**
   * 练习状态 0 保存 1 交卷
   */
  @NotEmpty
  @LegalValueList(value = {"0", "1"})
  private String examStatus;

  /**
   * 答题时间
   */
  @NotEmpty
  private String examTime;
  /**
   * 练习详情
   */
  @NotEmpty
  private String examDetail;

  /** recordId 挑战记录ID, 练习类型为挑战时必传 */
  private String recordId;

  private String nextChapterSummery;

  public Integer getUserCredit() {
    return userCredit;
  }

  public void setUserCredit(Integer userCredit) {
    this.userCredit = userCredit;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getExamStatus() {
    return examStatus;
  }

  public void setExamStatus(String examStatus) {
    this.examStatus = examStatus;
  }

  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(String examDetail) {
    this.examDetail = examDetail;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getNextChapterSummery() {
    return nextChapterSummery;
  }

  public void setNextChapterSummery(String nextChapterSummery) {
    this.nextChapterSummery = nextChapterSummery;
  }

  public static class AnswerItem {
    /**
     * 问题ID
     */
    @NotEmpty
    private Long quesId;
    /**
     * 答案ID列表
     */
    private List<String> answerIdList = Lists.newArrayList();

    private String examStatus;

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }

    public Long getQuesId() {
      return quesId;
    }

    public void setQuesId(Long quesId) {
      this.quesId = quesId;
    }

    public List<String> getAnswerIdList() {
      return answerIdList;
    }

    public void setAnswerIdList(List<String> answerIdList) {
      this.answerIdList = answerIdList;
    }
  }

}
