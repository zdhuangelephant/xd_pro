package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.DailyExamRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 每日做题统计请求pojo
 * @version 1.0
 */
public class DailyExamPojo extends MapiBaseRequest {
  @NotEmpty
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
