package com.xiaodou.engine.scorepoint;

import java.util.List;

import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.manager.facade.QuesOperationFacade;

/**
 * @name @see com.xiaodou.engine.scorepoint.FinalExamScorePointCaculator.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 期末测试计分点计算器
 * @version 1.0
 */
public class FinalExamScorePointCaculator extends BaseScorePointCaculator {

  public FinalExamScorePointCaculator(UserScorePointRecord record) {
    super(record);
  }

  /** totalItemCount 期末测试总数 */
  private Integer totalExamCount = 0;

  /** completeItemCount 期末测试完成数 */
  private Integer completeExamCount = 0;

  /** totalItemScore 期末测试总分 */
  private Double totalExamScore = 0d;

  @Override
  public void caculateScore(QuesOperationFacade facade) {
    // 期末分
    List<FinalExamModel> finalExamList =
        facade.selectFinalExamByCond(getRecord().getProductId().toString(), getRecord().getUserId()
            .toString());
    if (finalExamList == null || finalExamList.size() == 0) {
      return;
    }
    this.totalExamCount = finalExamList.size();
    for (FinalExamModel finalExam : finalExamList) {
      if (finalExam.getScore() == null) {
        continue;
      }
      Double finalExamScore = Double.valueOf(finalExam.getScore());
      this.totalExamScore += finalExamScore;
      if (finalExamScore >= 60) {
        this.completeExamCount++;
      }
    }
    setScore(caculateFinalExamAvgScore());
    setCompletePercent(caculateFinalExamCompletePercent());
  }

  /**
   * <p>
   * 计算期末测试平均成绩分
   * </p>
   * 期末测试计算节点总分 / 期末测试计算节点个数 * 期末测试计算节点权重
   * 
   * @return itemCaculateScore
   */
  private Double caculateFinalExamAvgScore() {
    if (totalExamCount == 0) {
      return 0d;
    }
    return totalExamScore / totalExamCount * 1d;
  }

  /**
   * 计算期末测试完成度
   * 
   * @return
   */
  private Double caculateFinalExamCompletePercent() {
    if (totalExamCount == 0) {
      return 0d;
    }
    return completeExamCount / totalExamCount * 1d;
  }
}
