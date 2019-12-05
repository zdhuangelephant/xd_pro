package com.xiaodou.ms.model.mission;

import lombok.Data;

/**
 * @author zdh:
 * @version 1.0
 * @date 2017年5月23日 下午5:19:57
 * 
 */
@Data
public class MissionModel {
	public MissionModel() {}
	/** id 主键ID */
	private String id;
	/** module 所属模块 */
	private String module;
	/** majorId 专业ID */
	private String majorId;
	/** courseId 课程ID */
	private String courseId;
	/** courseId 课程名字 */
	private String courseName;
	/** chapterId 章ID */
	private String chapterId;
	/** missionType 任务类型 */
	private String missionType;
	/** preCondList 前置条件列表 */
	private String preCondList;
	/** condType 条件类型 */
	private String condType;
	/** condition 条件 */
	private String condition;
	/** scope 取值范围 */
	private String scope;
	/** threshold 取值范围阀值 */
	private String threshold;
	/** missionName 任务名 */
	private String missionName;
	/** missionDesc 任务描述 */
	private String missionDesc;
	/** missionPicUrl 任务图片 */
	private String missionPicUrl;
	/** jumpType 跳转类型 */
	private String jumpType;
	/** taskType 任务类型明细 */
	private String taskType;
	/** missionState 任务状态 */
	private Short missionState;
	/** missionOrder 任务顺序 */
	private Integer missionOrder;
	/** creditUpper 积分涨幅 */
	private Integer creditUpper;
	/** redBounsType 红包奖励类型 */
	private String redBonusType;
	/** expiryDate 有效期 */
	private Integer expiryDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
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
	public String getChapterId() {
		return chapterId;
	}
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	public String getMissionType() {
		return missionType;
	}
	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}
	public String getPreCondList() {
		return preCondList;
	}
	public void setPreCondList(String preCondList) {
		this.preCondList = preCondList;
	}
	public String getCondType() {
		return condType;
	}
	public void setCondType(String condType) {
		this.condType = condType;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getMissionName() {
		return missionName;
	}
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}
	public String getMissionDesc() {
		return missionDesc;
	}
	public void setMissionDesc(String missionDesc) {
		this.missionDesc = missionDesc;
	}
	public String getMissionPicUrl() {
		return missionPicUrl;
	}
	public void setMissionPicUrl(String missionPicUrl) {
		this.missionPicUrl = missionPicUrl;
	}
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Short getMissionState() {
		return missionState;
	}
	public void setMissionState(Short missionState) {
		this.missionState = missionState;
	}
	public Integer getMissionOrder() {
		return missionOrder;
	}
	public void setMissionOrder(Integer missionOrder) {
		this.missionOrder = missionOrder;
	}
	public Integer getCreditUpper() {
		return creditUpper;
	}
	public void setCreditUpper(Integer creditUpper) {
		this.creditUpper = creditUpper;
	}
	public String getRedBonusType() {
		return redBonusType;
	}
	public void setRedBonusType(String redBonusType) {
		this.redBonusType = redBonusType;
	}
	public Integer getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Integer expiryDate) {
		this.expiryDate = expiryDate;
	}

	
}
