package com.xiaodou.course.vo.product;

import java.util.List;

/**
 * Created by zyp on 15/8/24.
 */
public class StudyStatistic {

  // 今天学习时长
  private Integer todayStudyTime;

  // 总计学习时长
  private Integer totalTime;

  // 学习记录
  private List<StudyTime> lastStudyTime;

  public List<StudyTime> getLastStudyTime() {
    return lastStudyTime;
  }

  public void setLastStudyTime(List<StudyTime> lastStudyTime) {
    this.lastStudyTime = lastStudyTime;
  }

  public Integer getTodayStudyTime() {
    return todayStudyTime;
  }

  public void setTodayStudyTime(Integer todayStudyTime) {
    this.todayStudyTime = todayStudyTime;
  }

  public Integer getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(Integer totalTime) {
    this.totalTime = totalTime;
  }
}
