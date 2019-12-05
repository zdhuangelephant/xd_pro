package com.xiaodou.server.mapi.request.product;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 
 * @name LearnRecordRequest
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月1日
 * @description 学习记录入参bean
 * @version 1.0
 */
public class LearnRecordViewRequest extends MapiBaseRequest {
  @NotEmpty
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
