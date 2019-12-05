package com.xiaodou.st.dataclean.model.transport;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.st.dataclean.model.transport.TransferFinishMissionData.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月5日
 * @description 完成任务数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferFinishMissionData extends BaseTransferModel {
  /** userId 用户ID */
  @NotEmpty
  private String userId;
  /** majorId 专业ID */
  @NotEmpty
  private String majorId;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** currentTime 当前日期 */
  @NotEmpty
  private Timestamp currentTime;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public Timestamp getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Timestamp currentTime) {
    this.currentTime = currentTime;
  }

}
