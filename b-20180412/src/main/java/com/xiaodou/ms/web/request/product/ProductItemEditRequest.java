package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/7/6.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductItemEditRequest extends BaseRequest {

  private Long id;

  // 父章节
  private Long parentId;

  // 章节名
  private String name;

  /** pictureUrl 章节封面图片地址 */
  private String pictureUrl;

  // 详情
  private String detail;

  // 是否显示
  private Integer showStatus;

  // 是否免费
  private Integer isFree;

  // 章节名称
  private String chapterId;

  // 重要度
  private Integer importanceLevel;

  // 排序
  private Long listOrder = 0L;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
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

public String getPictureUrl() {
	return pictureUrl;
}

public void setPictureUrl(String pictureUrl) {
	this.pictureUrl = pictureUrl;
}

public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
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

public Long getListOrder() {
	return listOrder;
}

public void setListOrder(Long listOrder) {
	this.listOrder = listOrder;
}

 
}
