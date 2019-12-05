package com.xiaodou.ms.web.request.ruide;

import java.sql.Timestamp;

import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zdh:
 * @date 2017年6月7日
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAddRequest extends BaseRequest {
	private long id;
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
	public ActivityModel initModel() {
		ActivityModel model = new ActivityModel();
		model.setTitle(title);
		model.setSubtitle(subtitle);
		model.setActivityPlace(activityPlace);
		model.setActivityTime(activityTime);
		model.setDeadlineTime(deadlineTime);
		model.setTutorId(tutorId);
		model.setContent(content);
		model.setId(id);
		model.setContentImage(contentImage);
		return model;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
}
