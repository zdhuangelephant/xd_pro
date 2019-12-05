package com.xiaodou.st.dataclean.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.st.dataclean.model.transport.TransferFinishAllMissionData.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月5日
 * @description 完成全部任务数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferFinishAllMissionData extends BaseTransferModel {
  /** userId 用户ID */
  @NotEmpty
  private String userId;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** majorId 专业ID */
  @NotEmpty
  private String majorId;

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

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }
}
