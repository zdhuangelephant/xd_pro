package com.xiaodou.moofficial.entity;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 
 * @ClassName: MajorCourseModel
 * @Description: 课程Model
 * @author zhouhuan
 * @date 2016年7月1日
 */
public class MajorCourseModel {

	// 课程ID
	@Column(isMajor = true)
	private String id;

	// 课程名称
	private String name;

	// 课程介绍
	private String detail;

	private String examDate;
	// 创建时间
	private Timestamp createTime;
	// 课程信息
	private String majorCourseInfo;
	// 课程性质
	private String courseType;
	// 学分
	private String credit;
	// 考核方式
	private String mode;
	// 考期
	private String examDateType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getMajorCourseInfo() {
		return majorCourseInfo;
	}

	public void setMajorCourseInfo(String majorCourseInfo) {
		this.majorCourseInfo = majorCourseInfo;
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

	public void initMajorCourseInfo(String majorCourseInfo) {
		if (StringUtils.isJsonNotBlank(majorCourseInfo)) {
			MajorCourseInfo mInfo = FastJsonUtil.fromJson(majorCourseInfo, MajorCourseInfo.class);
			if (null != mInfo) {
				if (StringUtils.isNotBlank(mInfo.getDetail()))
					detail = mInfo.getDetail();
				if (StringUtils.isNotBlank(mInfo.getExamDate()))
					examDate = mInfo.getExamDate();
				if (StringUtils.isNotBlank(mInfo.getCredit()))
					credit = mInfo.getCredit();
				if (StringUtils.isNotBlank(mInfo.getCourseType()))
					courseType = mInfo.getCourseType();
				if (StringUtils.isNotBlank(mInfo.getMode()))
					mode = mInfo.getMode();
				if (mInfo.getCreateTime() != null)
					createTime = mInfo.getCreateTime();
				if (StringUtils.isNotBlank(mInfo.getExamDateType()))
					examDateType = mInfo.getExamDateType();
			}
		}
	}

	public String getExamDateType() {
		return examDateType;
	}

	public void setExamDateType(String examDateType) {
		this.examDateType = examDateType;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(MajorCourseModel.class, "xd_major_course", "src/main/resources/conf/mybatis")
				.buildXml();
	}
}
