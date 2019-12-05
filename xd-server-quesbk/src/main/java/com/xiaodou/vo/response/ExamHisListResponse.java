package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamRecordDetail;

public class ExamHisListResponse extends BaseResponse {

  public ExamHisListResponse(ResultType type) {
    super(type);
  }

  /**
   * 练习列表
   */
  private List<Exam> examList = Lists.newArrayList();

  public List<Exam> getExamList() {
    return examList;
  }

  public void setExamList(List<Exam> examList) {
    this.examList = examList;
  }

  public static class Exam {
    /**
     * 练习名称
     */
    private String examName;
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
     * 练习耗时
     */
    private Long examCost;
    /**
     * 练习类型
     */
    private String examType;
    /**
     * 练习状态 0 保存 1 交卷
     */
    private String examStatus;
    /**
     * 练习详情
     */
    private ExamDetail examDetail;

    public Exam(UserExamRecord record) {
      UserExamRecordDetail recordDetail =
          FastJsonUtil.fromJson(record.getExamDetail(), UserExamRecordDetail.class);
      setExamId(record.getId().toString()); // 设置练习ID
      setExamName(record.getExamName()); // 设置练习名称
      setExamTime(QuesBaseConstant.DATE_FORMAT.format(record.getExamTime())); // 设置练习时间
      setExamCost(record.getExamCost()); // 设置练习耗时
      setExamType(record.getExamTypeId().toString()); // 设置练习类型
      setPaperId(record.getPaperNo().toString()); // 设置试卷编号
      setExamStatus(recordDetail.getStatus()); // 设置上次提交状态
      ExamHisListResponse.ExamDetail examDetail = new ExamHisListResponse.ExamDetail(); // 练习进度详情
      examDetail.setQuesCount(recordDetail.getQuesCount().toString()); // 设置问题数量
      examDetail.setAccuracy(recordDetail.getAccuracy().toString()); // 设置正确率
      examDetail.setFinishCount(recordDetail.getFinishCount().toString()); // 设置完成数量
      examDetail.setFinishPercent(recordDetail.getFinishPercent().toString()); // 设置完成比例
      examDetail.setCreditUpper(recordDetail.getCreditUpper().toString()); // 设置积分涨幅
      examDetail.setScore(recordDetail.getScore().toString()); // 设置得分
      examDetail.setStarLevel(recordDetail.getStarLevel().toString()); // 设置星级
      setExamDetail(examDetail);
    }

    public Long getExamCost() {
      return examCost;
    }

    public void setExamCost(Long examCost) {
      this.examCost = examCost;
    }

    public String getExamName() {
      return examName;
    }

    public void setExamName(String examName) {
      this.examName = examName;
    }

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }

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

    public ExamDetail getExamDetail() {
      return examDetail;
    }

    public void setExamDetail(ExamDetail examDetail) {
      this.examDetail = examDetail;
    }
  }

  public static class ExamDetail {
    /**
     * 问题数量
     */
    private String quesCount;
    /**
     * 完成数量
     */
    private String finishCount;
    /**
     * 完成比例
     */
    private String finishPercent;
    /**
     * 正确率
     */
    private String accuracy;
    /** starLevel 星级 */
    private String starLevel;
    /** score 得分 */
    private String score;
    /** creditUpper 积分涨幅 */
    private String creditUpper;

    public String getQuesCount() {
      return quesCount;
    }

    public void setQuesCount(String quesCount) {
      this.quesCount = quesCount;
    }

    public String getFinishCount() {
      return finishCount;
    }

    public void setFinishCount(String finishCount) {
      this.finishCount = finishCount;
    }

    public String getFinishPercent() {
      return finishPercent;
    }

    public void setFinishPercent(String finishPercent) {
      this.finishPercent = finishPercent;
    }

    public String getAccuracy() {
      return accuracy;
    }

    public void setAccuracy(String accuracy) {
      this.accuracy = accuracy;
    }

    public String getStarLevel() {
      return starLevel;
    }

    public void setStarLevel(String starLevel) {
      this.starLevel = starLevel;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getCreditUpper() {
      return creditUpper;
    }

    public void setCreditUpper(String creditUpper) {
      this.creditUpper = creditUpper;
    }

  }
}
