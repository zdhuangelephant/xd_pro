package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.PerformanceDetailPojo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 成绩详情参数pojo
 * @version 1.0
 */
public class PerformanceDetailPojo extends BaseRequest {

  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** bonusId 红包ID */
  private String bonusId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getBonusId() {
    return bonusId;
  }

  public void setBonusId(String bonusId) {
    this.bonusId = bonusId;
  }

}
