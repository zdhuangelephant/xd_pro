package com.xiaodou.server.mapi.response.selftaught;

import com.xiaodou.summer.vo.out.ResultType;

public class CourseDetailResponse extends BaseResponse{
	
	public CourseDetailResponse(ResultType type){
		super(type);
	}
	
	private Course courseDetail = new Course();
	
	
	public Course getCourseDetail() {
		return courseDetail;
	}


	public void setCourseDetail(Course courseDetail) {
		this.courseDetail = courseDetail;
	}


	public static class Course {
		private String courseName="a";
		private String backGroundImage= "http://q.qlogo.cn/qqapp/1104876114/7871C6077F809AB76DE2716D765BBD4A/40";
		private String backGroundText ="aa";
		private String degree="1";
		private String major = "1";
		private String examDate = "2016年1月25日";
		private String description = "aa";
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getBackGroundImage() {
			return backGroundImage;
		}
		public void setBackGroundImage(String backGroundImage) {
			this.backGroundImage = backGroundImage;
		}
		public String getBackGroundText() {
			return backGroundText;
		}
		public void setBackGroundText(String backGroundText) {
			this.backGroundText = backGroundText;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getMajor() {
			return major;
		}
		public void setMajor(String major) {
			this.major = major;
		}
		public String getExamDate() {
			return examDate;
		}
		public void setExamDate(String examDate) {
			this.examDate = examDate;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
}
