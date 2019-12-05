package com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.session_percent.CategorySessionPercentModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 专业学期平均数据
 * @version 1.0
 */
public class CategorySessionPercentModel {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer catId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer chiefUnitId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double learnPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Double missionPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double rightPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double learnTimePercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer studentCount;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer roleType;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer unitId;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Double getLearnPercent() {
		return learnPercent;
	}

	public void setLearnPercent(Double learnPercent) {
		this.learnPercent = learnPercent;
	}

	public Double getMissionPercent() {
		return missionPercent;
	}

	public void setMissionPercent(Double missionPercent) {
		this.missionPercent = missionPercent;
	}

	public Double getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(Double rightPercent) {
		this.rightPercent = rightPercent;
	}

	public Integer getChiefUnitId() {
		return chiefUnitId;
	}

	public void setChiefUnitId(Integer chiefUnitId) {
		this.chiefUnitId = chiefUnitId;
	}

	public Double getLearnTimePercent() {
		return learnTimePercent;
	}

	public void setLearnTimePercent(Double learnTimePercent) {
		this.learnTimePercent = learnTimePercent;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
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

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						CategorySessionPercentModel.class,
						"xd_dashboard_category_session_percent",
						"E:/work3/xd-dashboard-2b/src/main/resources/conf/mybatis/dashboard/session_percent/")
				.buildXml();
	}
}
