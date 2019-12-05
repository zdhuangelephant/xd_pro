package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 
 * @name ModifyScoreDateRequest
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月15日
 * @description 更新课程学习得分入参bean
 * @version 1.0
 */
public class ModifyScoreDateRequest extends BaseRequest {
  @NotEmpty
  private String courseId;
  @NotEmpty
  private String recordTime;// 考虑到如果通过异步消息发送，有延时的情况下，所以需要把时间（format:0000-00-00）也传过来
  @NotEmpty
  private String score;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(String recordTime) {
    this.recordTime = recordTime;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }
}
