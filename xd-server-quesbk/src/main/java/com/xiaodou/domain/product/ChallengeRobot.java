package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChallengeRobot extends BaseEntity {

	/** id 主键ID */
	@Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private Long id;
	/** userId 用户ID */
	private Long userId;
	/** majorId 专业ID */
	private String majorId;
	/** courseId 课程ID */
	private Long courseId;
	/** targetAbility 目标能力值 */
	private Integer targetAbility;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						ChallengeRobot.class,
						"xd_challenge_robot",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
		;
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

	public Integer getTargetAbility() {
		return targetAbility;
	}

	public void setTargetAbility(Integer targetAbility) {
		this.targetAbility = targetAbility;
	}

}
