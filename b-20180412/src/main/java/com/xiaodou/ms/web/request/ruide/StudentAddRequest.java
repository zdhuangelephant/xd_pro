package com.xiaodou.ms.web.request.ruide;

import java.sql.Timestamp;

import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.StudentModel;
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
public class StudentAddRequest extends BaseRequest {
	private Long id;
	// 所修专业
	private Long majorId;
	// 感悟简介
	private String thinkDesc;
	// 姓名
	private String author;
	// 内容
	private String content;
	// 头像
	private String portrait;
	// 发布时间
	private Timestamp publishTime;
	/* 正文图片 */
	private String contentImage;
	public StudentModel initModel() {
		StudentModel model = new StudentModel();
		model.setAuthor(author);
		model.setContent(content);
		model.setId(id);
		model.setMajorId(majorId);
		model.setPortrait(portrait);
		model.setPublishTime(publishTime);
		model.setThinkDesc(thinkDesc);
		model.setContentImage(contentImage);
		return model;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMajorId() {
		return majorId;
	}
	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}
	public String getThinkDesc() {
		return thinkDesc;
	}
	public void setThinkDesc(String thinkDesc) {
		this.thinkDesc = thinkDesc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public String getContentImage() {
		return contentImage;
	}
	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}
	
}
