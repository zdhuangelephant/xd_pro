package com.xiaodou.resources.response.quesbk;

import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.response.BaseResponse;

/**
 * @name @see com.xiaodou.vo.response.CourseStatisticsResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月9日
 * @description 课程练习分析统计情况响应
 * @version 1.0
 */
public class CourseStatisticsResponse extends BaseResponse {

  public CourseStatisticsResponse() {}

  public CourseStatisticsResponse(ResultType resultType) {
    super(resultType);
  }

  private String score = "0";
  private String myQues = "0";
  private String myRightPercent = "0%";
  private String myWrongQues = "0";

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
