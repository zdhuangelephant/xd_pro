package com.xiaodou.resources.model.quesbk;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class UserCourseStatisticDayModel {
  /** id 主键 */
  @Column(isMajor = true)
  private Integer id;
  @Column(canUpdate = false)
  private Integer userId;
  @Column(canUpdate = false)
  private Integer productId;
  @Column(canUpdate = false)
  private Integer moduleId;
  private Integer score;
  /* recordTime 记录天数（时间） */
  private String recordTime;
  @Column(canUpdate = false)
  private Timestamp createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public String getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(String recordTime) {
    this.recordTime = recordTime;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserCourseStatisticDayModel.class, "xd_user_course_statistic_day",
        "D:\\work\\workspace_xiaodou/xd-server-quesbk/src/main/resources/conf/mybatis/").buildXml();
  }
}
