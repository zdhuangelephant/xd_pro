package com.xiaodou.st.dataclean.model.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.DaysLearnTimeModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 每日学习时长统计表已经弃用
 * @version 1.0
 */
public class DaysLearnTimeModel {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer studentId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer achieveMission;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer learnTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double rightPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String dateTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getAchieveMission() {
		return achieveMission;
	}

	public void setAchieveMission(Integer achieveMission) {
		this.achieveMission = achieveMission;
	}

	public Integer getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Integer learnTime) {
		this.learnTime = learnTime;
	}

	public Double getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(Double rightPercent) {
		this.rightPercent = rightPercent;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(DaysLearnTimeModel.class,
				"xd_dashboard_workbench_days_learn_time",
				"E:/work3/xd-dashboard-2b/src/main/resources/conf/mybatis/")
				.buildXml();
	}

}
