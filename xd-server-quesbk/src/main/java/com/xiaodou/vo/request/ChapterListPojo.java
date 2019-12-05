package com.xiaodou.vo.request;

/**
 * @name @see com.xiaodou.vo.request.ChapterListPojo.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月7日
 * @description 章节列表Pojo
 * @version 1.0
 */
public class ChapterListPojo extends QuesBasePojo {

  /** courseId 课程ID */
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
