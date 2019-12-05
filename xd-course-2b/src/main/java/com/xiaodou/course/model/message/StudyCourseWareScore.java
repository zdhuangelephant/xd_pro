package com.xiaodou.course.model.message;

import lombok.Data;

/**
 * @name @see com.xiaodou.vo.mq.StudyCourseWare.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月17日
 * @description 课件学习成绩
 * @version 1.0
 */
@Data
public class StudyCourseWareScore {
  /** module 地域码 */
  private String module;
  /** courseId 课程ID */
  private String courseId;
  /** userId 用户ID */
  private String userId;
  /** courseWareScore 课件学习成绩 */
  private Double courseWareScore;
}
