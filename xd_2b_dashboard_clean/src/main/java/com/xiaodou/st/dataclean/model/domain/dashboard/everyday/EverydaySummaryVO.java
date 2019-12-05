package com.xiaodou.st.dataclean.model.domain.dashboard.everyday;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * @name EverydaySummaryVO CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年4月11日
 * @description TODO
 * @version 1.0
 */
@Data
public class EverydaySummaryVO {
	@Column(isMajor = true)
	@GeneralField
	private Long id;
	/** totalStudents 总的学生人数 */
	@Column(canUpdate = true, sortBy = false)
	@GeneralField
	private Long totalStudents;
	/** totalSubjectsAndStus 总科次 */
	@Column(canUpdate = true, sortBy = false)
	@GeneralField
	private Long totalSubjectsAndStus;
	/** learnZeroCount 无学习记录科次 */
	// @GeneralField
	// @Column(canUpdate = true, sortBy = false)
	// private Integer learnNoneCounts;
	/** learnPercent 学习使用率 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double learnPercent;
	/** passPercent 及格率 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double passPercent;
	/** passPercent 0分科次占比 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double zeroPercent;
	/** roleType 角色类型 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer roleType;
	/** unitId 执行单元ID */
	@GeneralField
	@Column(canUpdate = true, sortBy = true, betweenScope = true, listValue = true)
	private Integer unitId;
	/** updateTime 学习记录更新时间 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false, betweenScope = true)
	private Timestamp updateTime;
	/** createTime 创建时间 */
	@GeneralField
	@Column(canUpdate = true, sortBy = true, betweenScope = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(EverydaySummaryVO.class,
				"xd_dashboard_everyday_summary_statistics",
				"src/main/resources/conf/mybatis/dashboard/everyday/")
				.buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTotalStudents() {
		return totalStudents;
	}

	public void setTotalStudents(Long totalStudents) {
		this.totalStudents = totalStudents;
	}

	public Long getTotalSubjectsAndStus() {
		return totalSubjectsAndStus;
	}

	public void setTotalSubjectsAndStus(Long totalSubjectsAndStus) {
		this.totalSubjectsAndStus = totalSubjectsAndStus;
	}

	public Double getLearnPercent() {
		return learnPercent;
	}

	public void setLearnPercent(Double learnPercent) {
		this.learnPercent = learnPercent;
	}

	public Double getPassPercent() {
		return passPercent;
	}

	public void setPassPercent(Double passPercent) {
		this.passPercent = passPercent;
	}

	public Double getZeroPercent() {
		return zeroPercent;
	}

	public void setZeroPercent(Double zeroPercent) {
		this.zeroPercent = zeroPercent;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
