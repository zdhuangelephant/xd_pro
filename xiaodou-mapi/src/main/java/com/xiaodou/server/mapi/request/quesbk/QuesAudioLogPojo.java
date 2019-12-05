package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 20171109
 * @description 答题音频日志request
 * @version 1.0
 */
public class QuesAudioLogPojo extends MapiBaseRequest {
  // 题目Id
  @NotEmpty
  private String quesId;
  // 题目答案音频url
  @NotEmpty
  private String quesVideoUrl;

  @NotEmpty
  private String courseId;

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

  public String getQuesVideoUrl() {
    return quesVideoUrl;
  }

  public void setQuesVideoUrl(String quesVideoUrl) {
    this.quesVideoUrl = quesVideoUrl;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }
}
