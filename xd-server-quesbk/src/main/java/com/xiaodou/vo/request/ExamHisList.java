package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ExamHisList extends QuesBasePojo {

	/**
	 * 课程ID
	 */
	@NotEmpty
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
