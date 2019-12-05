package com.xiaodou.st.dataclean.model.domain.dashboard.lastweek;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.last_week.
 *       LastWeekLearnTimeModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 上周学霸榜数据模型
 * @version 1.0
 */
@Data
public class LastWeekLearnTimeModel {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer rank;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer studentId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer learnTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer answerCount;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private String rightPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer roleType;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer unitId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp createTime;
	// 传递参数
	/** taughtUnitId 自考办ID */
	@Column(persistent = false)
	private Integer taughtUnitId;
	// 主考院校Id
	@Column(persistent = false)
	private Integer chiefUnitId;
	// 试点单位Id
	@Column(persistent = false)
	private Integer pilotUnitId;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(LastWeekLearnTimeModel.class,
				"xd_dashboard_last_week_learn_time",
				"src/main/resources/conf/mybatis/dashboard/last_week/")
				.buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Integer learnTime) {
		this.learnTime = learnTime;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public String getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getTaughtUnitId() {
		return taughtUnitId;
	}

	public void setTaughtUnitId(Integer taughtUnitId) {
		this.taughtUnitId = taughtUnitId;
	}

	public Integer getChiefUnitId() {
		return chiefUnitId;
	}

	public void setChiefUnitId(Integer chiefUnitId) {
		this.chiefUnitId = chiefUnitId;
	}

	public Integer getPilotUnitId() {
		return pilotUnitId;
	}

	public void setPilotUnitId(Integer pilotUnitId) {
		this.pilotUnitId = pilotUnitId;
	}
}
