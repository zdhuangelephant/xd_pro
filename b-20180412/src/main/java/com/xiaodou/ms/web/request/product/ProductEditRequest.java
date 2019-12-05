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
public class ProductEditRequest extends BaseRequest {

	// 课程ID
	@NotEmpty
	private Long id;

	// 课程名称
	private String name;

	// 课程详情
	private String detail;

	// 原价
	private BigDecimal originalAmount;

	// 优惠价
	private BigDecimal payAmount;

	// 展示状态
	private Integer showStatus;

	// 封面URL
	private String imageUrl;

	// 简介
	private String briefInfo;

	// 分享地址
	private String shareUrl;

	// 资源产品ID
	private Long resourceSubject;
	// 开始时间
	private String beginApplyTime;

	// 结束时间
	private String endApplyTime;

	// 新手课程
	private String moduleCourse;

	// 所属地域
	private String module;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getResourceSubject() {
		return resourceSubject;
	}

	public void setResourceSubject(Long resourceSubject) {
		this.resourceSubject = resourceSubject;
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

}
