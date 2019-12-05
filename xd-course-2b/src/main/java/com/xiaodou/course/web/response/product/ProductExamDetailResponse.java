package com.xiaodou.course.web.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.vo.product.ExamDateDetail;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ProductExamDetailResponse extends BaseResponse {

  public ProductExamDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public ProductExamDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  /** examDateDetail 课程考期详情 */
  private List<ExamDateDetail> examDateDetail = Lists.newArrayList();
  /** intervalsBetweenExam 考期间隔天数 */
  private Long intervalsBetweenExam = 0l;
  /** awayNextExam 距下次考期剩余天数 */
  private Long awayNextExam = 0l;
  /** awayPreEaxm 距上次考期已过天数  备考天数*/
  private Long awayPreEaxm = -1l;//无法确定

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
