package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class BaseDetailPojo extends QuesBasePojo {

	/**
	 * 課程ID
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
