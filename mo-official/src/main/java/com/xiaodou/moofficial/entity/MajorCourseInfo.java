package com.xiaodou.moofficial.entity;

import java.sql.Timestamp;

import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @ClassName: MajorCourseInfo
 * @Description: 课程详细信息
 * @author zhouhuan
 * @date 2016年7月1日
 */
public class MajorCourseInfo {
	// 课程介绍
	private String detail;
	// 考试时间
	private String examDate;
	// 创建时间
	private Timestamp createTime;
	// 课程性质
	private String courseType;
	// 学分
	private String credit;
	// 考核方式
	private String mode;
	// 考期
	private String examDateType;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getExamDateType() {
		return examDateType;
	}

	public void setExamDateType(String examDateType) {
		this.examDateType = examDateType;
	}

}
