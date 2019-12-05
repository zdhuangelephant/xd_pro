package com.xiaodou.autopractise.request;

import lombok.Data;

/**
 * @name @see com.xiaodou.autopractise.request.CourseInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月4日
 * @description 课程信息请求类
 * @version 1.0
 */
@Data
public class CourseInfo {
  private String id;
  private String name;
  private String type;
}
