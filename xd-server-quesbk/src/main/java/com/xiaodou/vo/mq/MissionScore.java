package com.xiaodou.vo.mq;

/**
 * @name @see com.xiaodou.domain.RedBonus.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年7月20日
 * @description 平常分
 * @version 1.0
 */
public class MissionScore {
	private String userId;
	private String courseId;
	private String missionCount;
	private String finishMissionCount;
    private String module;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getMissionCount() {
		return missionCount;
	}

	public void setMissionCount(String missionCount) {
		this.missionCount = missionCount;
	}

	public String getFinishMissionCount() {
		return finishMissionCount;
	}

	public void setFinishMissionCount(String finishMissionCount) {
		this.finishMissionCount = finishMissionCount;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
