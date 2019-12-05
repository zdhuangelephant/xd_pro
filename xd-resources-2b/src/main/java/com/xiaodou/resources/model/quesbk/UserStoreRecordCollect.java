package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

/**
 * @name @see com.xiaodou.domain.UserStoreRecordCollect.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月13日
 * @description 用户收藏统计记录
 * @version 1.0
 */
public class UserStoreRecordCollect extends BaseEntity {
	/** id 主键ID */
	private Long id;

	/** userId 用户ID */
	private String userId;

	/** courseId 课程ID */
	private Long courseId;

	/** chapterId 章节ID */
	private Long chapterId;

	/** chapterName 章节名称 */
	private String chapterName;

	/** parentChapter 父章节ID */
	private Long parentChapter;

	/** parentChapterName 父章节ID */
	private String parentChapterName;

	/** questionNumber 问题数 */
	private int questionNumber = 0;
	private int goodQuesCount = 0;// 好题数量
	private int unknownQuesCount = 0;// 我不会的问题数量
	private int needMemeryQuesCount = 0; // 需要记忆问题数量

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

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Long getParentChapter() {
		return parentChapter;
	}

	public void setParentChapter(Long parentChapter) {
		this.parentChapter = parentChapter;
	}

	public String getParentChapterName() {
		return parentChapterName;
	}

	public void setParentChapterName(String parentChapterName) {
		this.parentChapterName = parentChapterName;
	}

	public Long getId() {
		return id;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public int getGoodQuesCount() {
		return goodQuesCount;
	}

	public void setGoodQuesCount(int goodQuesCount) {
		this.goodQuesCount = goodQuesCount;
	}

	public int getUnknownQuesCount() {
		return unknownQuesCount;
	}

	public void setUnknownQuesCount(int unknownQuesCount) {
		this.unknownQuesCount = unknownQuesCount;
	}

	public int getNeedMemeryQuesCount() {
		return needMemeryQuesCount;
	}

	public void setNeedMemeryQuesCount(int needMemeryQuesCount) {
		this.needMemeryQuesCount = needMemeryQuesCount;
	}

	public UserStoreRecordCollect() {
	}

	public UserStoreRecordCollect(UserStoreRecord record) {
		this.courseId = record.getCourseId();
		this.userId = record.getUserId();
		this.chapterId = record.getChapterId();
	}

}
