package com.xiaodou.autopractise.request;

import java.util.List;

import lombok.Data;

/**
 * @name @see com.xiaodou.autopractise.request.CourseTestInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月7日
 * @description 课程测验结果
 * @version 1.0
 */
@Data
public class CourseTestInfo {
  private String name;
  private String courseName;
  private List<TestInfo> grageArr;

  /**
   * @name @see com.xiaodou.autopractise.request.CourseTestInfo.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年5月7日
   * @description 测试信息
   * @version 1.0
   */
  @Data
  public static class TestInfo {
    /** index 所属阶段 */
    private Integer index;
    /** time 练习次数 */
    private Integer time;
    /** score 成绩 */
    private String score;
    /** date 练习日期 */
    private String date;
  }
}
