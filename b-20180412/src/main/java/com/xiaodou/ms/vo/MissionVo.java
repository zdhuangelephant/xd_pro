package com.xiaodou.ms.vo;

import lombok.Data;

@Data
public class MissionVo {
	/** missionId 任务ID */
	private String missionId;
	// standard 11节任务 12章任务 13期末测试 dynamic 21 消灭错题 22 每日一练 23 随机PK static 33
	// 完善信息 34 笔记
	private String missionType;
	/** courseId 课程ID */
	private String courseId;
	/** chapterId 章ID */
	private String chapterId;
	/** chapterIndex 章序号 */
	private String chapterIndex;
	/** chapterName 章名称 */
	private String chapterName;
	/** itemId 节ID */
	private String itemId;
	/** itemIndex 节序号 */
	private String itemIndex;
	/** itemName 节名称 */
	private String itemName;
	/** desc 显示信息 */
	private String desc;
	/** threshold 阈值 */
	private String threshold;
	/** listOrder 排序 */
	private Integer listOrder;
	/** missionOrder 任务排序字段 */
	private Integer missionOrder;
	// 闯关任务状态
	private String missionState;
	//地域
	private String module;

	

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterIndex() {
		return chapterIndex;
	}

	public void setChapterIndex(String chapterIndex) {
		this.chapterIndex = chapterIndex;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(String itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public Integer getMissionOrder() {
		return missionOrder;
	}

	public void setMissionOrder(Integer missionOrder) {
		this.missionOrder = missionOrder;
	}

	public String getMissionState() {
		return missionState;
	}

	public void setMissionState(String missionState) {
		this.missionState = missionState;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	

}
