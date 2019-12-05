package com.xiaodou.ms.web.request.topic;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author huangtao Created by zyp on 15/7/20.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForumCategoryCreateRequest extends BaseRequest {
	// 话题分类id
	private Long id;
	// 话题分类标签id
	private Integer moudle = 1;
	// 话题标题
	@NotEmpty
	private String title;
	// 话题描述
	private String outline;
	// 话题内容
	private String content;
	// 话题简称
	private String shortName;
	// 话题图片地址
	private String images;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

}
