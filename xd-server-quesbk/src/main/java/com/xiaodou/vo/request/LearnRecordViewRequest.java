package com.xiaodou.vo.request;

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
public class LearnRecordViewRequest extends QuesBasePojo {

  @NotEmpty
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCacheKey() {
    return String.format("{type:learnRecordView,uid:%s;courseId:%s}", getUid(), getCourseId());
  }

}
