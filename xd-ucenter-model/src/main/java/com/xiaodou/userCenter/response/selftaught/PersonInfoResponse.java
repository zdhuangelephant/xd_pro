package com.xiaodou.userCenter.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class PersonInfoResponse extends BaseResultInfo {

	public PersonInfoResponse(ResultType type) {
		super(type);
	}

	public PersonInfoResponse(UcenterResType resType) {
		super(resType);
	}

	/**
	 * 用户信息
	 */
	private UserInfo userInfo;
	/**
	 * 课程信息
	 */
	private List<CourseInfo> courseInfoList = Lists.newArrayList();
	/**
	 * 赞列表
	 */
	private List<UserModel> praiseList = Lists.newArrayList();

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<CourseInfo> getCourseInfoList() {
		return courseInfoList;
	}

	public void setCourseInfoList(List<CourseInfo> courseInfoList) {
		this.courseInfoList = courseInfoList;
	}

	public List<UserModel> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<UserModel> praiseList) {
		this.praiseList = praiseList;
	}

	public static class UserInfo extends StUserInfoResponse {
		private String praiseNum;
		private String isPraised;

		public UserInfo getUserInfo(UserModelVo vo) {
			this.setAddress(vo.getAddress());
			this.setAge(vo.getAge().toString());
			this.setGender(vo.getGender().toString());
			this.setNickName(vo.getNickName());
			this.setPortrait(vo.getPortrait());
			return this;
		}

		public String getPraiseNum() {
			return praiseNum;
		}

		public void setPraiseNum(String praiseNum) {
			this.praiseNum = praiseNum;
		}

		public String getIsPraised() {
			return isPraised;
		}

		public void setIsPraised(String isPraised) {
			this.isPraised = isPraised;
		}
	}

	public static class CourseInfo {
		private String courseId;
		private String courseName;
		private String score;

		public CourseInfo() {
		}

		public CourseInfo(String courseId, String courseName, String score) {
			this.courseId = courseId;
			this.courseName = courseName;
			this.score = score;
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

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
	}

}
