package com.xiaodou.vo.response;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.behavior.UserExamRecord.ExamDate;
import com.xiaodou.domain.behavior.UserExamTotal.ChapterScore;

/**
 * @name @see com.xiaodou.vo.response.DailyExamResponse.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 每日做题统计响应
 * @version 1.0
 */
public class DailyExamResponse extends BaseResponse {

  public DailyExamResponse(ResultType type) {
    super(type);
  }
  /*课程总成绩*/
  private String totalScore = "0";
  /** totalQues 每天总做题数 */
  private String todayQues = "0";
  /** totalQues 总做题数 */
  private String totalQues = "0";
  /** totalQues 每天pk(挑战)题数 */
  private String todayPkQues = "0";
  /** totalQues pk(挑战)题数 */
  private String totalPkQues = "0";
  /** 章节成绩 */
  private List<ChapterScore> chapterScoreList = new ArrayList<ChapterScore>();
  /** 学习时间 */
  private List<ExamDate> examDateList = Lists.newArrayList();

  public String getTodayQues() {
    return todayQues;
  }

  public void setTodayQues(String todayQues) {
    this.todayQues = todayQues;
  }

  public String getTotalQues() {
    return totalQues;
  }

  public void setTotalQues(String totalQues) {
    this.totalQues = totalQues;
  }

  public String getTodayPkQues() {
    return todayPkQues;
  }

  public void setTodayPkQues(String todayPkQues) {
    this.todayPkQues = todayPkQues;
  }

  public String getTotalPkQues() {
    return totalPkQues;
  }

  public void setTotalPkQues(String totalPkQues) {
    this.totalPkQues = totalPkQues;
  }

  public String getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(String totalScore) {
    this.totalScore = totalScore;
  }

  public List<ChapterScore> getChapterScoreList() {
    return chapterScoreList;
  }

  public void setChapterScoreList(List<ChapterScore> chapterScoreList) {
    this.chapterScoreList = chapterScoreList;
  }

  public List<ExamDate> getExamDateList() {
    return examDateList;
  }

  public void setExamDateList(List<ExamDate> examDateList) {
    this.examDateList = examDateList;
  }

}
