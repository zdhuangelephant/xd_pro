package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnScoreDateResponse extends BaseResponse {

  public UserLearnScoreDateResponse() {}

  public UserLearnScoreDateResponse(ResultType type) {
    super(type);
  }

  private List<ScoreDate> scoreDateList = Lists.newArrayList();

  public List<ScoreDate> getScoreDateList() {
    return scoreDateList;
  }

  public void setScoreDateList(List<ScoreDate> scoreDateList) {
    this.scoreDateList = scoreDateList;
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
      this.examDate = examDate;
    }

    public String getDateCourseScore() {
      return MathUtil.getIntStringValue(dateCourseScore);
    }

    public void setDateCourseScore(String dateCourseScore) {
      this.dateCourseScore = dateCourseScore;
    }
  }
}
