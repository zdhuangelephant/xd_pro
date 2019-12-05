package com.xiaodou.resources.util;

import com.xiaodou.resources.model.product.ProductModel;

public class ScoreCaculator {

  /** talkWeight 单元讨论权重 */
  private Integer talkWeight = 0;

  /** taskWeight 单元作业权重 */
  private Integer taskWeight = 0;

  /** examWeight 单元测验权重 */
  private Integer examWeight = 0;

  /** finalWeight 期末考试权重 */
  private Integer finalWeight = 0;

  public ScoreCaculator(ProductModel product) {
    this.examWeight = product.getExamWeight();
    this.taskWeight = product.getTaskWeight();
    this.talkWeight = product.getTalkWeight();
    this.finalWeight = product.getFinalWeight();
  }

  public Double caculate(UserScoreDetail scoreDetail) {
    Integer total = this.examWeight + this.taskWeight + this.talkWeight + this.finalWeight;
    Double totalScore =
        scoreDetail.examScore * this.examWeight + scoreDetail.taskScore * this.taskWeight
            + scoreDetail.talkScore * this.talkWeight + scoreDetail.finalScore * this.finalWeight;
    return totalScore / total;
  }

  public static class UserScoreDetail {
    private Double examScore;
    private Double taskScore;
    private Double talkScore;
    private Double finalScore;
    public Double getExamScore() {
      return examScore;
    }

    public void setExamScore(Double examScore) {
      this.examScore = examScore;
    }

    public Double getTaskScore() {
      return taskScore;
    }

    public void setTaskScore(Double taskScore) {
      this.taskScore = taskScore;
    }

    public Double getTalkScore() {
      return talkScore;
    }

    public void setTalkScore(Double talkScore) {
      this.talkScore = talkScore;
    }

    public Double getFinalScore() {
      return finalScore;
    }

    public void setFinalScore(Double finalScore) {
      this.finalScore = finalScore;
    }
  }
}
