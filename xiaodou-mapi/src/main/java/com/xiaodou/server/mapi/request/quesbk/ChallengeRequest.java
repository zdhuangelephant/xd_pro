package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ChallengeRequest extends MapiBaseRequest {
  /**
   * 课程ID
   * 
   */
  @NotEmpty
  private String type;// 挑战类型 1 挑战好友 2 随机挑战
  @NotEmpty(field = "type", value = "1")
  private String userId;// 挑战用户ID // type == 1必传
  @NotEmpty
  private String courseId;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
