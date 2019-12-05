package com.xiaodou.userCenter.module.selfTaught.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.CourseModel;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class StConfigResponse extends
		ConfigResponse<StConfigResponse.ConfigData> {

	public StConfigResponse(ResultType type) {
		super(type);
	}

	public StConfigResponse(UcenterResType type) {
		super(type);
	}

	public static class ConfigData {
		/** major 专业列表 */
		private List<Major> majorList = Lists.newArrayList();// 所有专业

		/** thirdlogin 三方登录 */
		private List<String> thirdlogin = Lists.newArrayList();
		/** shareplateform 分享平台 */
		private List<String> shareplateform = Lists.newArrayList();
		/** blankNotice 系统公告和活动 */
		private BlankNotice blankNotice;

		public List<Major> getMajorList() {
			return majorList;
		}

		public void setMajorList(List<Major> majorList) {
			this.majorList = majorList;
		}

		public void setBlankNotice(BlankNotice blankNotice) {
			this.blankNotice = blankNotice;
		}

		public BlankNotice getBlankNotice() {
			return blankNotice;
		}

		public void setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
			BlankNotice blankNotice = new BlankNotice();
			this.blankNotice = blankNotice.setBlankNotice(blankNoticeResponse);
		}

		public List<String> getThirdlogin() {
			return thirdlogin;
		}

		public void setThirdlogin(List<String> thirdlogin) {
			this.thirdlogin = thirdlogin;
		}

		public List<String> getShareplateform() {
			return shareplateform;
		}

		public void setShareplateform(List<String> shareplateform) {
			this.shareplateform = shareplateform;
		}

	}

	public static class Major {
		/** majorId 专业唯一标识 */
		private String majorId;
		/** majorName 专业名称 */
		private String majorName;
		private String majorImage;
		private String degree;

		private List<Course> latestCourseList;// 此专业下的热门课程

		private List<Course> courseList;// 其它课程

		public String getMajorId() {
			return majorId;
		}

		public void setMajorId(String majorId) {
			this.majorId = majorId;
		}

		public String getMajorName() {
			return majorName;
		}

		public void setMajorName(String majorName) {
			this.majorName = majorName;
		}

		public String getMajorImage() {
			return majorImage;
		}

		public void setMajorImage(String majorImage) {
			this.majorImage = majorImage;
		}

		public String getDegree() {
			return degree;
		}

		public void setDegree(String degree) {
			this.degree = degree;
		}

		public List<Course> getLatestCourseList() {
			return latestCourseList;
		}

		public void setLatestCourseList(List<Course> latestCourseList) {
			this.latestCourseList = latestCourseList;
		}

		public List<Course> getCourseList() {
			return courseList;
		}

		public void setCourseList(List<Course> courseList) {
			this.courseList = courseList;
		}

	}

	public static class Course {
		private String courseId;
		private String courseName;
		private String courseImage;
		private String examDate;

		public Course getCourse(CourseModel courseModel) {
			this.courseId = courseModel.getCourseId();
			this.courseImage = courseModel.getCourseImage();
			this.courseName = courseModel.getCourseName();
			this.examDate = courseModel.getExamDate();
			return this;
		}

		public String getCourseId() {
			return courseId;
		}

		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseImage() {
			return courseImage;
		}

		public void setCourseImage(String courseImage) {
			this.courseImage = courseImage;
		}

		public String getExamDate() {
			return examDate;
		}

		public void setExamDate(String examDate) {
			this.examDate = examDate;
		}
	}

	/**
	 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
	 * @description 首页弹出公告实体类
	 * @version 1.0
	 */
	public static class BlankNotice {
		// 展示方式：0 每次都展示，1 只展示一次，2 不展示
		private String type;
		// 展示地址 http://
		private String showUrl;
		// 跳转地址 http:// app://
		private String jumpUrl;

		public BlankNotice() {
			super();
		}

		public BlankNotice(String type, String showUrl, String jumpUrl) {
			super();
			this.type = type;
			this.showUrl = showUrl;
			this.jumpUrl = jumpUrl;
		}

		public BlankNotice setBlankNotice(
				BlankNoticeResponse blankNoticeResponse) {
			return new BlankNotice(blankNoticeResponse.getType().toString(),
					blankNoticeResponse.getShowUrl(),
					blankNoticeResponse.getJumpUrl());
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getShowUrl() {
			return showUrl;
		}

		public void setShowUrl(String showUrl) {
			this.showUrl = showUrl;
		}

		public String getJumpUrl() {
			return jumpUrl;
		}

		public void setJumpUrl(String jumpUrl) {
			this.jumpUrl = jumpUrl;
		}
	}

}
