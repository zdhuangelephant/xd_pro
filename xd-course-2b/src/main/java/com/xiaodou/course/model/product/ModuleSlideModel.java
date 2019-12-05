package com.xiaodou.course.model.product;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by zyp on 15/8/16.
 */
@Data
public class ModuleSlideModel {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图片地址
	 */
	private String imageUrl;

	/**
	 * 连接地址
	 */
	private String linkUrl;

	/**
	 * 排序
	 */
	private Integer listOrder;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 模块Id
	 */
	private Integer moduleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

}
