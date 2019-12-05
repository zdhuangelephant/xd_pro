package com.xiaodou.resources.task.quesbk;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.service.quesbk.QuesBatchProService;
import com.xiaodou.summer.sceduling.concurrent.SummerTask;

public class UserExamRightRank extends SummerTask {

  private Integer selfRank;

  private UserExamTotal myExam;

  private QuesBatchProService proService;

  public UserExamRightRank(QuesBatchProService proService, UserExamTotal myExam, Integer selfRank) {
    this.myExam = myExam;
    this.proService = proService;
    this.selfRank = selfRank;
  }

  @Override
  public void onException(Throwable t) {
    LoggerUtil.error("批处理失败", t);
    // 报警
  }

  @Override
  public void run() {
    // 更新全部用户的统计
    try {
      // 针对单个学生平均正确答题排名
      proService.updateNotAllUserRight(myExam, selfRank);
    } catch (Exception e) {
      LoggerUtil.error("批处理更新数据异常", e);
    }
  }

}
