package com.xiaodou.server.mapi.response.quesbk;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class CourseStatisticsInfo extends BaseResponse {

  public CourseStatisticsInfo() {}

  public CourseStatisticsInfo(ResultType resultType) {
    super(resultType);
  }

  private String score;
  private String myQues;
  private String myRightPercent;
  private String myWrongQues;

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getMyQues() {
    return myQues;
  }

  public void setMyQues(String myQues) {
    this.myQues = myQues;
  }

  public String getMyRightPercent() {
    return myRightPercent;
  }

  public void setMyRightPercent(String myRightPercent) {
    this.myRightPercent = myRightPercent;
  }

  public String getMyWrongQues() {
    return myWrongQues;
  }

  public void setMyWrongQues(String myWrongQues) {
    this.myWrongQues = myWrongQues;
  }

}
