package com.xiaodou.domain.behavior;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class UserCourseStatisticDayModel {
	/** id 主键 */
	@Column(isMajor = true)
	private Long id;
	@Column(canUpdate = false)
	private Long userId;
	@Column(canUpdate = false)
	private Long productId;
	@Column(canUpdate = false)
	private Integer moduleId;
	private Integer score;
	/* recordTime 记录天数（时间） */
	private String recordTime;
	@Column(canUpdate = false)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						UserCourseStatisticDayModel.class,
						"xd_user_course_statistic_day",
						"D:/work/workspace_xd/xd-server-quesbk-remould/src/main/resources/conf/mybatis/behavior/")
				.buildXml();
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

}
