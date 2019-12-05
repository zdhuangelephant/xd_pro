package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 发布话题request
 * 
 * @author bing.cheng
 *
 */
public class ForumRequest extends BaseRequest {

	/** 话题名称 */
	@NotEmpty
	private String title;

	/** 话题内容 */
	@NotEmpty
	private String content;

	/** 话题图片 前端传入，以逗号分割 */
	private String images;

	/** 话题分类 */
	@NotEmpty
	private String categoryId;

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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

}
