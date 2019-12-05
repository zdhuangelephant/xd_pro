package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;

/**
 * @name @see com.xiaodou.vo.request.CourseStatisticsPojo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月9日
 * @description 课程学习统计情况参数pojo
 * @version 1.0
 */
public class CourseStatisticsPojo extends BaseRequest {

  /** courseId 课程ID */
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }



}
