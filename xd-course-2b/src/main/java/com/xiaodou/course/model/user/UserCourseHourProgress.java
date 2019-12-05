package com.xiaodou.course.model.user;

import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.course.web.request.product.LearnRecordRequest.PointVo;

import lombok.Data;

@Data
public class UserCourseHourProgress {
  /** id 主键 */
  @Column(isMajor = true, sortBy = true, listValue = true)
  @GeneralField
  private Long id;
  /** module 地域 */
  @GeneralField
  @Column(sortBy = true)
  private String module;
  /** courseId 课程 */
  @GeneralField
  @Column(sortBy = true)
  private String courseId;
  /** chapterId 章 */
  @GeneralField
  @Column(sortBy = true)
  private String chapterId;
  /** itemId 节 */
  @GeneralField
  @Column(sortBy = true)
  private String itemId;
  /** keyPointId 考点ID*/
  @GeneralField
  @Column(listValue = true, sortBy = true)
  private String keyPointId;
  /** userId 用户 */
  @GeneralField
  @Column(listValue = true, sortBy = true)
  private String userId;
  /** resourceType 资源ID*/
  @GeneralField
  @Column(listValue = true, sortBy = true)
  private Integer resourceType;
  /** duration 总时长 */
  @GeneralField
  private Long duration;
  /** learnTime 资源学习时长(秒为单位)*/
  @GeneralField
  private Long learnTime;
  /** status 1/0, finished / unfinished */
  @GeneralField
  private Integer status;
  
  
  private List<PointVo> points;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserCourseHourProgress.class, "xd_user_course_hour_progress",
        "src/main/resources/conf/mybatis/user/").buildXml();
  }
}
