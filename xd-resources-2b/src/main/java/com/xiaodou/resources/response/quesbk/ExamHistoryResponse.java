package com.xiaodou.resources.response.quesbk;

import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.enums.quesbk.ExamTimeType;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.response.BaseResponse;

/**
 * @name ExamHistoryResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月31日
 * @description 练习历史响应
 * @version 1.0
 */
public class ExamHistoryResponse extends BaseResponse {
  public ExamHistoryResponse() {}

  public ExamHistoryResponse(ResultType type) {
    super(type);
  }

  private ExamInfo examInfo;
  private List<ExamRecordInfo> examRecordInfoList = Lists.newArrayList();

  public ExamInfo getExamInfo() {
    return examInfo;
  }

  public void setExamInfo(ExamInfo examInfo) {
    this.examInfo = examInfo;
  }

  public List<ExamRecordInfo> getExamRecordInfoList() {
    return examRecordInfoList;
  }

  public void setExamRecordInfoList(List<ExamRecordInfo> examRecordInfoList) {
    this.examRecordInfoList = examRecordInfoList;
  }

  /**
   * @name ExamInfo CopyRright (c) 2016 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年10月31日
   * @description 测验信息
   * @version 1.0
   */
  public static class ExamInfo {
    /** examTimeLimit 测验限时 */
    private String examTimeLimit;
    /** examFrequency 最大提交次数 */
    private Integer examFrequency;
    /** submitTimes 已提交次数 */
    private Integer submitTimes;
    /** deadLine 截止时间 */
    private String deadLine;
    /** currentScore 当前得分 */
    private Double currentScore;
    /** finalScore 最终得分 */
    private Double finalScore;
    /** examStatus 测验状态 */
    private Integer examStatus;

    public ExamInfo(ProductItemModel item) {
      if (null == item) return;
      this.examTimeLimit = ExamTimeType.getByTypeId(item.getExamTime()).getTypeName();
      this.examFrequency = null == item.getFrequency() ? 1 : item.getFrequency();
      this.deadLine =
          null == item.getDeadline() ? CourseConstant.NODEADLINE : DateUtil.SDF_FULL.format(item
              .getDeadline());
      this.examStatus =
          new Timestamp(System.currentTimeMillis()).before(item.getDeadline())
              ? QuesBaseConstant.EXAM_STATUS_NORMAL
              : QuesBaseConstant.EXAM_STATUS_FROZEN;
      this.currentScore = 0d;
      this.finalScore = 0d;
    }

    public String getExamTimeLimit() {
      return examTimeLimit;
    }

    public void setExamTimeLimit(String examTimeLimit) {
      this.examTimeLimit = examTimeLimit;
    }

    public Integer getExamFrequency() {
      return examFrequency;
    }

    public void setExamFrequency(Integer examFrequency) {
      this.examFrequency = examFrequency;
    }

    public Integer getSubmitTimes() {
      return submitTimes;
    }

    public void setSubmitTimes(Integer submitTimes) {
      this.submitTimes = submitTimes;
    }

    public String getDeadLine() {
      return deadLine;
    }

    public void setDeadLine(String deadLine) {
      this.deadLine = deadLine;
    }

    public Double getCurrentScore() {
      return currentScore;
    }

    public void setCurrentScore(Double currentScore) {
      this.currentScore = currentScore;
    }

    public Double getFinalScore() {
      return finalScore;
    }

    public void setFinalScore(Double finalScore) {
      this.finalScore = finalScore;
    }

    public Integer getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(Integer examStatus) {
      this.examStatus = examStatus;
    }
  }

  /**
   * @name ExamRecordInfo CopyRright (c) 2016 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年10月31日
   * @description 测验记录信息
   * @version 1.0
   */
  public static class ExamRecordInfo {
    /** recordId 记录ID */
    private String recordId;
    /** score 测验成绩 */
    private String score;
    /** examTime 测验时间 */
    private String examTime;

    public ExamRecordInfo(UserExamRecord record) {
      this.recordId = record.getId().toString();
      this.score = QuesBaseConstant.D_FORMAT.format(record.getMyScore());
      this.examTime = DateUtil.SDF_FULL.format(record.getExamTime());
    }

    public String getRecordId() {
      return recordId;
    }

    public void setRecordId(String recordId) {
      this.recordId = recordId;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getExamTime() {
      return examTime;
    }

    public void setExamTime(String examTime) {
      this.examTime = examTime;
    }
  }

}
