package com.xiaodou.st.dashboard.domain.session;

public class CategoryUnitSessionProductAvgScoreDTO {
  /* 课程名称 */
  private String productName;
  /* 课程平均成绩 */
  private String avgScore = "0";

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getAvgScore() {
    return avgScore;
  }

  public void setAvgScore(String avgScore) {
    this.avgScore = avgScore;
  }
}
