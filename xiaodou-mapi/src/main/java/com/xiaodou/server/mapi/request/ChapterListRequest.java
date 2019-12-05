package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ChapterListRequest extends MapiBaseRequest{

	/**
	 * 课程ID
	 * 
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
