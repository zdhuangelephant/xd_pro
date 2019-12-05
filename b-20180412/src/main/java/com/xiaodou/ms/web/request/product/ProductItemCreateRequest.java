package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/7/6.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductItemCreateRequest extends BaseRequest {

	// 产品Id
	private Long productId;

	// 父章节
	private Long parentId;

	// 章节名
	private String name;

	// 详情
	private String detail;

	// 资源Id
	private Long resourceId;

	// 资源类型
	private Integer resourceType;

	// 是否显示
	private Integer showStatus;

	// 是否免费
	private Integer isFree;

	// 章节名称
	private String chapterId;

	// 重要度
	private Integer importanceLevel;

	// 章节封面
	private String pictureUrl;

	// 排序
	private Integer listOrder = 0;

	// 地域
	private String module;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public Integer getImportanceLevel() {
		return importanceLevel;
	}

	public void setImportanceLevel(Integer importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
