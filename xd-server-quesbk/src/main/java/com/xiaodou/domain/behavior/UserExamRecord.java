package com.xiaodou.domain.behavior;

import java.util.Date;

import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.util.QuesbkUtil;

public class UserExamRecord extends BaseEntity {
  private Long id;

  private String userId;

  private Long examTypeId;

  private Long courseId;

  private String examName;

  private String paperNo;

  private String questions;

  private Date examTime;

  private String examDetail;

  private Long examCost;

  private QuesbkQues quesDetail = new QuesbkQues();

  private Double myScore;

  private ExamDate examDate = new ExamDate();

  public Double getMyScore() {
    return myScore;
  }

  public void setMyScore(Double myScore) {
    this.myScore = myScore;
  }

  public Long getExamTypeId() {
    return examTypeId;
  }

  public void setExamTypeId(Long examTypeId) {
    this.examTypeId = examTypeId;
  }

  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = QuesbkUtil.trim(examName);
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getExamCost() {
    return examCost;
  }

  public void setExamCost(Long examCost) {
    this.examCost = examCost;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public QuesbkQues getQuesDetail() {
    return quesDetail;
  }

  public void setQuesDetail(QuesbkQues quesDetail) {
    this.quesDetail = quesDetail;
  }

  public ExamDate getExamDate() {
    return examDate;
  }

  public void setExamDate(ExamDate examDate) {
    this.examDate = examDate;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId == null ? null : userId.trim();
  }

  public String getPaperNo() {
    return paperNo;
  }

  public String getQuestions() {
    return questions;
  }

  public void setQuestions(String questions) {
    this.questions = questions == null ? null : questions.trim();
  }

  public Date getExamTime() {
    return examTime;
  }

  public void setExamTime(Date examTime) {
    this.examTime = examTime;
  }

  public String getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(String examDetail) {
    this.examDetail = examDetail == null ? null : examDetail.trim();
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCourseId(String courseId) {
    if (StringUtils.isNotBlank(courseId)) {
      setCourseId(Long.parseLong(QuesbkUtil.trim(courseId)));
    }
  }

  public void setPaperNo(String paperId) {
    if (StringUtils.isNotBlank(paperId)) {
      paperNo = QuesbkUtil.trim(paperId);
    }
  }


public static class ExamDate {
    /* 日期eg:02-10 */
    private String examDate = StringUtils.EMPTY;
    /* 学习时长 */
    private String dateExamCost = StringUtils.EMPTY;

    public ExamDate() {}

    public ExamDate(String examDate) {
      this.examDate = examDate;
    }

    public ExamDate(String examDate, String dateExamCost) {
      super();
      this.examDate = examDate;
      this.dateExamCost = dateExamCost;
    }

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      // if (StringUtils.isNotBlank(examDate) && examDate.length() >= 5)
      // this.examDate = examDate.substring(5);
      // else
      this.examDate = examDate;
    }

    public String getDateExamCost() {
      return StringUtils.isNotBlank(dateExamCost) ? MathUtil.getIntStringValue(Double
          .valueOf(dateExamCost) / 60) : "0";
    }

    public void setDateExamCost(String dateExamCost) {
      this.dateExamCost = dateExamCost;
    }

    public static void main(String[] args) {
      String a = "6";
      System.out.println(Double.valueOf(a));
      System.out.println(Double.valueOf(a) / 60);
    }
  }

  public static class ScoreDate {
    /* 日期eg:09-12 */
    private String examDate = StringUtils.EMPTY;
    /* 当天课程得分 */
    private String dateCourseScore = String.valueOf(0);

    public ScoreDate() {}

    public ScoreDate(String examDate, String dateCourseScore) {
      super();
      this.examDate = examDate;
      this.dateCourseScore = MathUtil.getIntStringValue(dateCourseScore);
    }

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      // if (StringUtils.isNotBlank(examDate) && examDate.length() >= 5)
      // this.examDate = examDate.substring(5);
      // else
      this.examDate = examDate;
    }

    public String getDateCourseScore() {
      return dateCourseScore;
    }

    public void setDateCourseScore(String dateCourseScore) {
      this.dateCourseScore = MathUtil.getIntStringValue(dateCourseScore);
    }
  }
}
