package com.xiaodou.ms.model.robot;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

@Data
public class ChallengeRobotModel {
  /** 主键 */
  @GeneralField
  @Column(isMajor = true)
  private Long id;
  /** 用户ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private Long userId;
  /** 专业ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private String majorId;
  /** 课程ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private Long courseId;
  /** 课程ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private Long categoryId;
  /** 目标能力值 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private Integer targetAbility;
  /**module 地域 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private String module;
  /** 创建时间 */
  private Timestamp createTime;

  /** 专业名 */
  private String majorName;

  /** 课程名 */
  private String courseName;

  private String moduleName;
 

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(ChallengeRobotModel.class, "xd_challenge_robot",
            "E:\\newWorks\\eclipseWorks\\xd-ms2b\\src\\main\\resources\\conf\\mybatis\\robot")
        .buildXml();
  }


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public Long getUserId() {
	return userId;
}


public void setUserId(Long userId) {
	this.userId = userId;
}


public String getMajorId() {
	return majorId;
}


public void setMajorId(String majorId) {
	this.majorId = majorId;
}


public Long getCourseId() {
	return courseId;
}


public void setCourseId(Long courseId) {
	this.courseId = courseId;
}


public Long getCategoryId() {
	return categoryId;
}


public void setCategoryId(Long categoryId) {
	this.categoryId = categoryId;
}


public Integer getTargetAbility() {
	return targetAbility;
}


public void setTargetAbility(Integer targetAbility) {
	this.targetAbility = targetAbility;
}


public String getModule() {
	return module;
}


public void setModule(String module) {
	this.module = module;
}


public Timestamp getCreateTime() {
	return createTime;
}


public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}


public String getMajorName() {
	return majorName;
}


public void setMajorName(String majorName) {
	this.majorName = majorName;
}


public String getCourseName() {
	return courseName;
}


public void setCourseName(String courseName) {
	this.courseName = courseName;
}


public String getModuleName() {
	return moduleName;
}


public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}

}
