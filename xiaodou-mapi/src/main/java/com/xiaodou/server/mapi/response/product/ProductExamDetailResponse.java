package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class ProductExamDetailResponse extends BaseResponse {

  public ProductExamDetailResponse() {}

  public ProductExamDetailResponse(ResultType resultType) {
    super(resultType);
  }

  /** examDateDetail 课程考期详情 */
  private List<ExamDateDetail> examDateDetail = Lists.newArrayList();
  /** intervalsBetweenExam 考期间隔天数 */
  private Long intervalsBetweenExam = 0l;
  /** awayNextExam 距下次考期剩余天数 */
  private Long awayNextExam = 0l;
  /** awayPreEaxm 距上次考期已过天数  备考天数 */
  private Long awayPreEaxm = 0l;

  public List<ExamDateDetail> getExamDateDetail() {
    return examDateDetail;
  }

  public void setExamDateDetail(List<ExamDateDetail> examDateDetail) {
    this.examDateDetail = examDateDetail;
  }

  public Long getIntervalsBetweenExam() {
    return intervalsBetweenExam;
  }

  public void setIntervalsBetweenExam(Long intervalsBetweenExam) {
    this.intervalsBetweenExam = intervalsBetweenExam;
  }

  public Long getAwayNextExam() {
    return awayNextExam;
  }

  public void setAwayNextExam(Long awayNextExam) {
    this.awayNextExam = awayNextExam;
  }

  public Long getAwayPreEaxm() {
    return awayPreEaxm;
  }

  public void setAwayPreEaxm(Long awayPreEaxm) {
    this.awayPreEaxm = awayPreEaxm;
  }

  public static class ExamDateDetail {

    /* 当天距离考期 天数 */
    private Integer distanceDayCount = -1;
    private Integer courseId;
    private String courseName;

    public Integer getDistanceDayCount() {
      return distanceDayCount;
    }

    public void setDistanceDayCount(Integer distanceDayCount) {
      this.distanceDayCount = distanceDayCount;
    }

    public Integer getCourseId() {
      return courseId;
    }

    public void setCourseId(Integer courseId) {
      this.courseId = courseId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }
    
  }
}
