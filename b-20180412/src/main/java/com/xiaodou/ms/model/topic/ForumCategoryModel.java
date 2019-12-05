package com.xiaodou.ms.model.topic;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @ClassName: ForumCategoryModel
 * @Description: 话题分类Model
 * @author tao.huang
 */
@Data
public class ForumCategoryModel {

	private Long id;
	private Integer moudle;
	private String shortName;

	private String title;

	private String content;

	private String outline;

	private String images;

	private Integer forumNumber;

	private Integer peopleNumber;

	private Integer tag;

	private String operator;

	private String operatorip;

	private Timestamp createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMoudle() {
		return moudle;
	}

	public void setMoudle(Integer moudle) {
		this.moudle = moudle;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getForumNumber() {
		return forumNumber;
	}

	public void setForumNumber(Integer forumNumber) {
		this.forumNumber = forumNumber;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorip() {
		return operatorip;
	}

	public void setOperatorip(String operatorip) {
		this.operatorip = operatorip;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
