package com.xiaodou.ms.model.ruide;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class ActivityModel {
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/* 标题 */
	private String title;
	/* 副标题 */
	private String subtitle;
	// 活动时间
	private Timestamp activityTime;
	// 截至时间
	private Timestamp deadlineTime;
	// 活动地点
	private String activityPlace;
	// 关联导师
	private Long tutorId;
	/* 内容 */
	private String content;
	/* 正文图片 */
	private String contentImage;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;

	private TutorMajorModel tutorMajorModel;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(ActivityModel.class, "xd_rd_activity",
				"F:/snippet/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/ruide/").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Timestamp getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Timestamp activityTime) {
		this.activityTime = activityTime;
	}

	public Timestamp getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Timestamp deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public String getActivityPlace() {
		return activityPlace;
	}

	public void setActivityPlace(String activityPlace) {
		this.activityPlace = activityPlace;
	}

	public Long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentImage() {
		return contentImage;
	}

	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public TutorMajorModel getTutorMajorModel() {
		return tutorMajorModel;
	}

	public void setTutorMajorModel(TutorMajorModel tutorMajorModel) {
		this.tutorMajorModel = tutorMajorModel;
	}
	
}
