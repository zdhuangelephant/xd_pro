package com.xiaodou.mission.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
@Xml(tableName = "xd_mission_user_task_hit_complete", outputDir = "src/main/resources/conf/mybatis")
public class UserTaskHitCompleteModel {
  @Column(isMajor = true, canUpdate = false)
  private Long id;
  @Column(canUpdate = false)
  private String module;
//  @Column(canUpdate = false)
//  private String majorId;
  @Column(canUpdate = false, listValue = true)
  private String courseId;
  @Column(canUpdate = false)
  private String uid;
  /** isComplete 当前任务状态*/
  @Column(canUpdate = false)
  private Integer isComplete;
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserTaskHitCompleteModel.class).buildXml();
  }
}
