package com.xiaodou.server.mapi.response.quesbk;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.constant.QuesBaseConstant;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

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

  public DailyExamResponse() {}

  public DailyExamResponse(ResultType type) {
    super(type);
  }

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
  private List<ChapterDist> chapterScoreList = new ArrayList<ChapterDist>();
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

  public List<ChapterDist> getChapterScoreList() {
    return chapterScoreList;
  }

  public void setChapterScoreList(List<ChapterDist> chapterScoreList) {
    this.chapterScoreList = chapterScoreList;
  }

  public List<ExamDate> getExamDateList() {
    return examDateList;
  }

  public void setExamDateList(List<ExamDate> examDateList) {
    this.examDateList = examDateList;
  }

  public static class ChapterDist {
    public ChapterDist() {}

    private Long chapterId = 0L;
    private String chapterIndex = StringUtils.EMPTY;
    private String chapterName = StringUtils.EMPTY;
    private Integer chapterScore = 0;
    private Integer rightNum = 0;
    private Integer totalNum = 0;

    public Long getChapterId() {
      return chapterId;
    }

    public void setChapterId(Long chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterIndex() {
      return chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
      this.chapterIndex = chapterIndex;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public Integer getChapterScore() {
      return chapterScore;
    }

    public void setChapterScore(Integer chapterScore) {
      this.chapterScore = chapterScore;
    }

    public void addRightNum() {
      this.rightNum++;
    }

    public Integer caculateScore() {
      if (null != rightNum && null != totalNum && rightNum > 0 && totalNum >= rightNum) {
        this.chapterScore = totalNum > 20 ? (rightNum * 100) / totalNum : (rightNum * 100) / 20;
      }
      return getChapterScore();
    }

    public void addTotalNum() {
      this.totalNum++;
    }

  }
  public static class ExamDate {
    /* 日期eg:02-10 */
    private String examDate = StringUtils.EMPTY;
    /* 学习时长 */
    private String dateExamCost = StringUtils.EMPTY;

    public ExamDate() {}

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      if (StringUtils.isNotBlank(examDate) && examDate.length() >= 5)
        this.examDate = examDate.substring(5);
      else
        this.examDate = examDate;
    }

    public String getDateExamCost() {
      return dateExamCost;
    }

    public void setDateExamCost(String dateExamCost) {
      this.dateExamCost = dateExamCost;
    }
  }

  public static class ScoreDate {
    /* 日期eg:09-12 */
    private String examDate = StringUtils.EMPTY;
    /* 当天课程得分 */
    private String dateCourseScore = String.valueOf(0);

    public ScoreDate() {}

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      if (StringUtils.isNotBlank(examDate) && examDate.length() >= 5)
        this.examDate = examDate.substring(5);
      else
        this.examDate = examDate;
    }

    public String getDateCourseScore() {
      return dateCourseScore;
    }

    public void setDateCourseScore(String dateCourseScore) {
      this.dateCourseScore = dateCourseScore;
    }
  }
  public static class ChapterScore {
    public ChapterScore() {}

    private String chapterId = String.valueOf(0L);
    private String chapterIndex = StringUtils.EMPTY;
    private String chapterName = StringUtils.EMPTY;
    private String chapterScore = QuesBaseConstant.D_FORMAT.format(0.00);
    private String chapterSummaryScore = QuesBaseConstant.D_FORMAT.format(0.00);
    
    private Long listOrder = -1l;
    private Double weight;

    public Long getListOrder() {
      return listOrder;
    }

    public void setListOrder(Long listOrder) {
      this.listOrder = listOrder;
    }

    public Double getWeight() {
      return weight;
    }

    public void setWeight(Double weight) {
      this.weight = weight;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterIndex() {
      return chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
      this.chapterIndex = chapterIndex;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public String getChapterScore() {
      return MathUtil.getIntStringValue(chapterScore);
    }

    public void setChapterScore(String chapterScore) {
      this.chapterScore = chapterScore;
    }

    public String getChapterSummaryScore() {
      return MathUtil.getIntStringValue(chapterSummaryScore);
    }

    public void setChapterSummaryScore(String chapterSummaryScore) {
      this.chapterSummaryScore = chapterSummaryScore;
    }
  }
}
