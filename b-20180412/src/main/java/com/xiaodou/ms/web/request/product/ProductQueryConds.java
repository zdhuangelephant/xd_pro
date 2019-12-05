package com.xiaodou.ms.web.request.product;

import lombok.Data;

@Data
public class ProductQueryConds {
  /** showStatus 展示状态 */
  private String showStatus;
  /** courseCode 课程码 */
  private String courseCode;
  /** name 课程产品名称 */
  private String name;
  /** courseId 课程ID*/
  private Long courseId;
  /** isExpired 是否过期 */
  private String isExpired;
  /** catId 专业码 */
  private Long catId;
  /** module 所属地域*/
  private String module;
public String getShowStatus() {
	return showStatus;
}
public void setShowStatus(String showStatus) {
	this.showStatus = showStatus;
}
public String getCourseCode() {
	return courseCode;
}
public void setCourseCode(String courseCode) {
	this.courseCode = courseCode;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Long getCourseId() {
	return courseId;
}
public void setCourseId(Long courseId) {
	this.courseId = courseId;
}
public String getIsExpired() {
	return isExpired;
}
public void setIsExpired(String isExpired) {
	this.isExpired = isExpired;
}
public Long getCatId() {
	return catId;
}
public void setCatId(Long catId) {
	this.catId = catId;
}
public String getModule() {
	return module;
}
public void setModule(String module) {
	this.module = module;
}
  
}
