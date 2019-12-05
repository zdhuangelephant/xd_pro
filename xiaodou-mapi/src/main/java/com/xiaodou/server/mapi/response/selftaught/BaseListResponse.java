package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class BaseListResponse extends BaseResponse {

	public BaseListResponse(ResultType type) {
		super(type);
	}

	private List<Course> baseList = Lists.newArrayList(new Course(),
			new Course());

	public List<Course> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<Course> baseList) {
		this.baseList = baseList;
	}

	public static class Course {
		private String courseName = "幼儿保教知识能力";
		private String courseImageUrl = "http://img.com";
		private String courseId = "90";
		private String expense = "0"; // 是否付费 0 免费 1 收费

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseImageUrl() {
			return courseImageUrl;
		}

		public void setCourseImageUrl(String courseImageUrl) {
			this.courseImageUrl = courseImageUrl;
		}

		public String getCourseId() {
			return courseId;
		}

		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}

		public String getExpense() {
			return expense;
		}

		public void setExpense(String expense) {
			this.expense = expense;
		}
	}
}
