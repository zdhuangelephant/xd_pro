package com.xiaodou.ms.web.request.product;

import java.math.BigDecimal;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductCreateRequest extends BaseRequest {

  // 产品名称
  private String name;

  // 课程详情
  private String detail;

  // 类型
  private Integer type;

  // 资源Id
  private Long resourceSubject;

  // 原价
  private BigDecimal originalAmount;

  // 优惠价
  private BigDecimal payAmount;

  // 展示状态
  private Integer showStatus = 0;

  // 封面URL
  private String imageUrl;

  // 简介
  private String briefInfo;

  // 分享地址
  private String shareUrl;

  // 新手课程  
  private String moduleCourse;

  //所属地域
  private String module;
  
  // 课程代码
  @NotEmpty
  private String courseCode;

  // 课程考期
  private String examDate;

  // 开始时间
  private String beginApplyTime;

  // 结束时间
  private String endApplyTime;

  /** pid 一键复制主键 */
  private Long id;

  /** 计分规则Id */
  private String ruleId;

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

public Integer getType() {
	return type;
}

public void setType(Integer type) {
	this.type = type;
}

public Long getResourceSubject() {
	return resourceSubject;
}

public void setResourceSubject(Long resourceSubject) {
	this.resourceSubject = resourceSubject;
}

public BigDecimal getOriginalAmount() {
	return originalAmount;
}

public void setOriginalAmount(BigDecimal originalAmount) {
	this.originalAmount = originalAmount;
}

public BigDecimal getPayAmount() {
	return payAmount;
}

public void setPayAmount(BigDecimal payAmount) {
	this.payAmount = payAmount;
}

public Integer getShowStatus() {
	return showStatus;
}

public void setShowStatus(Integer showStatus) {
	this.showStatus = showStatus;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public String getBriefInfo() {
	return briefInfo;
}

public void setBriefInfo(String briefInfo) {
	this.briefInfo = briefInfo;
}

public String getShareUrl() {
	return shareUrl;
}

public void setShareUrl(String shareUrl) {
	this.shareUrl = shareUrl;
}

public String getModuleCourse() {
	return moduleCourse;
}

public void setModuleCourse(String moduleCourse) {
	this.moduleCourse = moduleCourse;
}

public String getModule() {
	return module;
}

public void setModule(String module) {
	this.module = module;
}

public String getCourseCode() {
	return courseCode;
}

public void setCourseCode(String courseCode) {
	this.courseCode = courseCode;
}

public String getExamDate() {
	return examDate;
}

public void setExamDate(String examDate) {
	this.examDate = examDate;
}

public String getBeginApplyTime() {
	return beginApplyTime;
}

public void setBeginApplyTime(String beginApplyTime) {
	this.beginApplyTime = beginApplyTime;
}

public String getEndApplyTime() {
	return endApplyTime;
}

public void setEndApplyTime(String endApplyTime) {
	this.endApplyTime = endApplyTime;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getRuleId() {
	return ruleId;
}

public void setRuleId(String ruleId) {
	this.ruleId = ruleId;
}
  
}
