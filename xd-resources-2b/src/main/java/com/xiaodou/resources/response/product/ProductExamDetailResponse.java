package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.product.ExamDateDetail;
import com.xiaodou.summer.vo.out.ResultType;

public class ProductExamDetailResponse extends BaseResponse {

  public ProductExamDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public ProductExamDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  /** majorId 专业ID */
  private String majorId;
  /** examDateDetail 课程考期详情 */
  private List<ExamDateDetail> examDateDetail = Lists.newArrayList();
  /** intervalsBetweenExam 考期间隔天数 */
  private Long intervalsBetweenExam = 0l;
  /** awayNextExam 距下次考期剩余天数 */
  private Long awayNextExam = 0l;
  /** awayPreEaxm 距上次考期已过天数 */
  private Long awayPreEaxm = 0l;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

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

}
