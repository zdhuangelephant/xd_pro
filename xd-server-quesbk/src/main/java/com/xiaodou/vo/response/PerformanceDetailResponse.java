package com.xiaodou.vo.response;

import com.xiaodou.constant.ResultType;


/**
 * @name @see com.xiaodou.vo.response.PerformanceDetailResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 成绩详情展示
 * @version 1.0
 */
public class PerformanceDetailResponse extends BaseResponse {
  public PerformanceDetailResponse(ResultType type) {
    super(type);
  }

  public void setAward(CourseScore courseScore) {
    this.courseScore = courseScore;
  }

  private CourseScore courseScore;

  public CourseScore getCourseScore() {
    return courseScore;
  }

  public void setCourseScore(CourseScore courseScore) {
    this.courseScore = courseScore;
  }
}
