package com.xiaodou.course.vo.product;

import lombok.Data;

/**
 * Created by zyp on 15/8/16.
 */
@Data
public class ModuleSlide {
	// 模块Id
	private Integer moduleId;

	// 图片地址
	private String imageUrl;

	// 连接地址
	private String linkUrl;

	// 描述
	private String description;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/** title 标题 */
	// private String title;

	/**
	 * 图片地址
	 */
	// private String imageUrl;

	/**
	 * 连接地址
	 */
	// private String redirectUrl;

}
