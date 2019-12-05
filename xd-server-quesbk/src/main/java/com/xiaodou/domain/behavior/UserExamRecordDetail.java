package com.xiaodou.domain.behavior;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.util.QuesCaculateUtil.IQuesCaculateTask;


public class UserExamRecordDetail extends BaseEntity {
  public UserExamRecordDetail() {}

  public UserExamRecordDetail(String userId) {
    this.userId = userId;
  }

  @JSONField(serialize = false, deserialize = false)
  private IQuesCaculateTask caculateTask;
  private String userId;
  /**
   * 练习状态
   */
  private String status = "1";
  /**
   * 问题数量
   */
  private Integer quesCount = 0;
  /**
   * 完成数量
   */
  private Integer finishCount = 0;
  /**
   * 正确数量
   */
  private Integer rightCount = 0;
  /**
   * 完成比例
   */
  private Double finishPercent = 0d;
  /**
   * 正确率
   */
  private Double accuracy = 0d;
  /** starLevel 星级 */
  private Integer starLevel = 0;
  /** score 得分 */
  private Double score = 0d;
  /** creditUpper 积分涨幅 */
  private Integer creditUpper = 0;

  public String getUserId() {
    return userId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getRightCount() {
    return rightCount;
  }

  public void setRightCount(Integer rightCount) {
    this.rightCount = rightCount;
    if (0 != quesCount) accuracy = (double) rightCount / (double) quesCount;
  }

  public void _setRightCount(Integer rightCount) {
    this.rightCount = rightCount;
    if (0 != quesCount) accuracy = (double) rightCount / (double) quesCount;
    if (null != caculateTask) {
      setScore(caculateTask.getScore(this));
      setStarLevel(caculateTask.getStarLevel(this));
      setCreditUpper(caculateTask.getCreditUpper(this));
    }
  }

  public void addRightCount() {
    _setRightCount(++rightCount);
  }

  public Integer getQuesCount() {
    return quesCount;
  }

  public void setQuesCount(Integer quesCount) {
    this.quesCount = quesCount;
    if (0 != rightCount) accuracy = (double) rightCount / (double) quesCount;
  }

  public Integer getFinishCount() {
    return finishCount;
  }

  public void setFinishCount(Integer finishCount) {
    this.finishCount = finishCount;
    if (0 != quesCount) finishPercent = (double) finishCount / (double) quesCount;
  }

  public Double getFinishPercent() {
    return finishPercent;
  }

  public void setFinishPercent(Double finishPercent) {
    this.finishPercent = finishPercent;
  }

  public Double getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(Double accuracy) {
    this.accuracy = accuracy;
  }

  public Integer getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(Integer starLevel) {
    this.starLevel = starLevel;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Integer getCreditUpper() {
    return creditUpper;
  }

  public void setCreditUpper(Integer creditUpper) {
    this.creditUpper = creditUpper;
  }

  @JSONField(serialize = false, deserialize = false)
  public IQuesCaculateTask getCaculateTask() {
    return caculateTask;
  }

  public void setCaculateTask(IQuesCaculateTask caculateTask) {
    this.caculateTask = caculateTask;
  }

}
