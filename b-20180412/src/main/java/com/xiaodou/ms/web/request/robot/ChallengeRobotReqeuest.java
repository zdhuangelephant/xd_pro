package com.xiaodou.ms.web.request.robot;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChallengeRobotReqeuest extends BaseRequest {
  @Column(isMajor = true)
  private Long id;
  /** 用户ID */
  private Long userId;
  /** 专业ID */
  private String majorId;
  /** 地域module*/
  private String module;
  /** 课程ID */
  private Long courseId;

  private Long categoryId;
  /** 目标能力值 */
  private Integer targetAbility;
  /** 创建时间 */
  private Timestamp createTime;

  public ChallengeRobotModel initModel() {
    ChallengeRobotModel model = new ChallengeRobotModel();
    model.setId(id);
    model.setUserId(userId);
    model.setMajorId(majorId);
    model.setCourseId(courseId);
    model.setCategoryId(categoryId);
    model.setTargetAbility(targetAbility);
    model.setModule(module);
    return model;
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

public String getModule() {
	return module;
}

public void setModule(String module) {
	this.module = module;
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

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}


}
