package com.xiaodou.resources.util;

import java.text.DecimalFormat;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.resources.model.quesbk.UserExamRecordDetail;

public class QuesCaculateUtil {

  public interface IQuesCaculateTask {
    /**
     * 计算积分涨幅
     * 
     * @param rightCount
     * @return
     */
    public Integer getCreditUpper(UserExamRecordDetail detail);

    /**
     * 计算得分
     * 
     * @param rightCount
     * @return
     */
    public Double getScore(UserExamRecordDetail detail);

    /**
     * 计算星级
     * 
     * @param rightCount
     * @return
     */
    public Integer getStarLevel(UserExamRecordDetail detail);
  }

  private static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");

  private static final Map<String, IQuesCaculateTask> taskMap = Maps.newHashMap();

  private static final IQuesCaculateTask defaultTask = new IQuesCaculateTask() {

    public Integer getCreditUpper(UserExamRecordDetail detail) {
      return detail.getRightCount();
    }

    public Double getScore(UserExamRecordDetail detail) {
      return Double.parseDouble(D_FORMAT.format((new Double(detail.getRightCount()) / new Double(
          detail.getQuesCount())) * 100));
    }

    public Integer getStarLevel(UserExamRecordDetail detail) {
      Double score = detail.getScore();
      if (null != score) return score >= 100 ? 3 : score >= 80 ? 2 : score >= 60 ? 1 : 0;
      return detail.getRightCount() == detail.getQuesCount() ? 3 : detail.getRightCount() >= detail
          .getQuesCount() * 0.8 ? 2 : detail.getRightCount() >= detail.getQuesCount() * 0.6 ? 1 : 0;
    }
  };

  public static void regist(String courseId, IQuesCaculateTask task) {
    if (!taskMap.containsKey(courseId)) {
      taskMap.put(courseId, task);
    }
  }

  public static IQuesCaculateTask getTask(String courseId) {
    if (null == taskMap || !taskMap.containsKey(courseId)) return defaultTask;
    return taskMap.get(courseId);
  }
}
