package com.xiaodou.resources.response.quesbk;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.UserExamRecord.ScoreDate;
import com.xiaodou.resources.model.quesbk.UserExamTotal.ChapterScore;
import com.xiaodou.resources.response.BaseResponse;

/**
 * 
 * @name LearnRecordResponse
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月1日
 * @description 学习记录响应
 * @version 1.0
 */
public class LearnRecordViewResponse extends BaseResponse {

  public LearnRecordViewResponse(ResultType type) {
    super(type);
  }

  /* 课程总成绩 */
  private String totalScore = "0";
  /* 课程做题总正确率 */
  private String rightPercent = "0.00";
  /** totalQues 总做题数 */
  private String totalQues = "0";
  /** totalRightQues 总做正确题数 */
  private String totalRightQues = "0";
  /** todayQues 今日总做题数 */
  private String todayQues = "0";
  /* challengeLevelCount 闯关数目 */
  private String challengeLevelCount = "0";
  /* levelCount 关卡数目 */
  private String totalLevelCount = "0";
  /* achieveStar 获得星星数目 */
  private String achieveStarCount = "0";
  /* totalStarCount 总星星数目 */
  private String totalStarCount = "0";
  /* realName 用户姓名 */
  private String realName = StringUtils.EMPTY;
  /* admissionCardCode 准考证号 */
  private String admissionCardCode = StringUtils.EMPTY;
  /* chapterScoreList 章得分 */
  private List<ChapterScore> chapterScoreList = Lists.newArrayList();
  /** scoreDateList 每日得分（成绩） */
  private List<ScoreDate> scoreDateList = Lists.newArrayList();

  /** examDateList 学习时间 */
  // private List<ExamDate> examDateList = Lists.newArrayList();

  public List<ChapterScore> getChapterScoreList() {
    return chapterScoreList;
  }

  public void setChapterScoreList(List<ChapterScore> chapterScoreList) {
    this.chapterScoreList = chapterScoreList;
  }

  public String getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(String rightPercent) {
    this.rightPercent = rightPercent;
  }

  public String getTotalQues() {
    return totalQues;
  }

  public void setTotalQues(String totalQues) {
    this.totalQues = totalQues;
  }

  public String getTotalRightQues() {
    return totalRightQues;
  }

  public void setTotalRightQues(String totalRightQues) {
    this.totalRightQues = totalRightQues;
  }

  public String getTodayQues() {
    return todayQues;
  }

  public void setTodayQues(String todayQues) {
    this.todayQues = todayQues;
  }

  public String getChallengeLevelCount() {
    return challengeLevelCount;
  }

  public void setChallengeLevelCount(String challengeLevelCount) {
    this.challengeLevelCount = challengeLevelCount;
  }

  public String getAchieveStarCount() {
    return achieveStarCount;
  }

  public void setAchieveStarCount(String achieveStarCount) {
    this.achieveStarCount = achieveStarCount;
  }

  public List<ScoreDate> getScoreDateList() {
    return scoreDateList;
  }

  public void setScoreDateList(List<ScoreDate> scoreDateList) {
    this.scoreDateList = scoreDateList;
  }

  // public List<ExamDate> getExamDateList() {
  // return examDateList;
  // }
  //
  // public void setExamDateList(List<ExamDate> examDateList) {
  // this.examDateList = examDateList;
  // }

  public String getTotalLevelCount() {
    return totalLevelCount;
  }

  public void setTotalLevelCount(String totalLevelCount) {
    this.totalLevelCount = totalLevelCount;
  }

  public String getTotalStarCount() {
    return totalStarCount;
  }

  public void setTotalStarCount(String totalStarCount) {
    this.totalStarCount = totalStarCount;
  }

  public String getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(String totalScore) {
    this.totalScore = totalScore;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getAdmissionCardCode() {
    return admissionCardCode;
  }

  public void setAdmissionCardCode(String admissionCardCode) {
    this.admissionCardCode = admissionCardCode;
  }

}
