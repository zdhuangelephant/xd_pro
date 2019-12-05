package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @Location xd-quesbase/com.xiaodou.vo.request.RealExamListPojo.java.java
 * @Description
 * @Author <a href="mailto:">Administrator</a>
 * @Date 下午11:31:55
 */
public class RealExamListPojo extends QuesBasePojo {

	/**
	 * 科目ID
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
