package com.xiaodou.server.mapi.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.server.mapi.response.product.UserLearnRecordDataResponse.LearnRecordData;
import com.xiaodou.server.mapi.response.quesbk.DailyExamResponse.ChapterScore;
import com.xiaodou.server.mapi.response.quesbk.DailyExamResponse.ScoreDate;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

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

  public LearnRecordViewResponse() {}

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
  private List<LearnRecordData> learnList = Lists.newArrayList();


  /* pk(挑战)次数 总正确率 */
  private String pkRightPercent = "0.00";
  /** totalPkQues pk(挑战)次数 */
  private String totalPkNum = "0";
  /** totalPkRightQues pk(挑战)题数 总正确次数 */
  private String totalPkRightNum = "0";
  /* deadline 截止日期 */
  private String deadline = "2017年4月30日";
  /* learnStatus 学习状态 0 正常，1 已结束 */
  private String learnStatus = "0";


  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getTotalPkNum() {
    return totalPkNum;
  }

  public void setTotalPkNum(String totalPkNum) {
    this.totalPkNum = totalPkNum;
  }

  public String getLearnStatus() {
    return learnStatus;
  }

  public void setLearnStatus(String learnStatus) {
    this.learnStatus = learnStatus;
  }

  public String getPkRightPercent() {
    return pkRightPercent;
  }

  public void setPkRightPercent(String pkRightPercent) {
    this.pkRightPercent = pkRightPercent;
  }

  public String getTotalPkRightNum() {
    return totalPkRightNum;
  }

  public void setTotalPkRightNum(String totalPkRightNum) {
    this.totalPkRightNum = totalPkRightNum;
  }

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

  public List<LearnRecordData> getLearnList() {
    return learnList;
  }

  public void setLearnList(List<LearnRecordData> learnList) {
    this.learnList = learnList;
  }

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
    return MathUtil.getIntStringValue(totalScore);
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
