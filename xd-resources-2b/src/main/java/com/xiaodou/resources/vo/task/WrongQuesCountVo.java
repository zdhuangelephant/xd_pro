package com.xiaodou.resources.vo.task;

/**
 * @name @see com.xiaodou.vo.task.WrongQuesCountVo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月23日
 * @description 错题数量统计任务VO
 * @version 1.0
 */
public class WrongQuesCountVo {

  /** module 模块ID */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** courseId 课程ID */
  private String courseId;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
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
