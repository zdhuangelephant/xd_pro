package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

/**
 * @name @see com.xiaodou.domain.UserStoreRecord.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月13日
 * @description 用户收藏记录
 * @version 1.0
 */
public class UserStoreRecord extends BaseEntity {
	/** id 主键ID */
	private Long id;

	/** userId 用户ID */
	private String userId;

	/** courseId 课程ID */
	private Long courseId;

	/** chapterId 章节ID */
	private Long chapterId;

	/** questionId 问题ID */
	private Long questionId;
	/**
	 * 收藏状态   1 好题(默认)  2 我不会的  3 需要记忆  4 取消收藏
	 */
	private String storeStatus;

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = QuesbkUtil.trim(userId);
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getId() {
		return id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

}
