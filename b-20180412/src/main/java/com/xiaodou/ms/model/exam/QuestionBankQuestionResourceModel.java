package com.xiaodou.ms.model.exam;

import java.sql.Timestamp;

import com.xiaodou.ms.model.course.CourseSubjectModel;

/**
 * Created by zyp on 15/8/6.
 */
public class QuestionBankQuestionResourceModel {

	// 主键Id
	private Integer id;

	// 名称
	private String name;

	// 详情
	private String detail;

	// 创建时间
	private Timestamp createTime;

	// 新加字段 modified by zdh at 2017-05-23
	// 关联课程id
	private Integer courseId;
	// 关联课程name
	private String courseName;

	
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}
