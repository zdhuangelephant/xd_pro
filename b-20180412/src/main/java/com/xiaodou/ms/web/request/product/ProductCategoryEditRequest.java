package com.xiaodou.ms.web.request.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryEditRequest extends BaseRequest {

  // 课程分类ID
  @NotEmpty
  private Long id;

  // 课程分类父ID
  private Long parentId;

  // 课程分类名称
  private String name;

  /** pictureUrl 专业选择封面 */
  private String pictureUrl;

  /** showCover 列表展示封面 */
  private String showCover;

  private String module;

  // 课程分类详细介绍
  private String detail;

  // APP分类码
  private String typeCode;

  private String majorCode;

  private Integer showStatus;

  //zwj 新加字段
  private Integer isCooperation; //'是否为合作专业：1表示是，0表示否',
  private Integer isSync;//'是否为同步云测评：1表示是，0表示否',
  private Integer isBuy;//'是否为可以购买：1表示是，0表示否',
  private Integer courseCount;//'课程数量',
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
public String getShowCover() {
	return showCover;
}
public void setShowCover(String showCover) {
	this.showCover = showCover;
}
public String getModule() {
	return module;
}
public void setModule(String module) {
	this.module = module;
}
public String getDetail() {
	return detail;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public String getTypeCode() {
	return typeCode;
}
public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
}
public String getMajorCode() {
	return majorCode;
}
public void setMajorCode(String majorCode) {
	this.majorCode = majorCode;
}
public Integer getShowStatus() {
	return showStatus;
}
public void setShowStatus(Integer showStatus) {
	this.showStatus = showStatus;
}
public Integer getIsCooperation() {
	return isCooperation;
}
public void setIsCooperation(Integer isCooperation) {
	this.isCooperation = isCooperation;
}
public Integer getIsSync() {
	return isSync;
}
public void setIsSync(Integer isSync) {
	this.isSync = isSync;
}
public Integer getIsBuy() {
	return isBuy;
}
public void setIsBuy(Integer isBuy) {
	this.isBuy = isBuy;
}
public Integer getCourseCount() {
	return courseCount;
}
public void setCourseCount(Integer courseCount) {
	this.courseCount = courseCount;
}
  
}
