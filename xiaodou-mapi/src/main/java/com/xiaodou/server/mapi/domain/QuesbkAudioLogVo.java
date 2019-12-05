package com.xiaodou.server.mapi.domain;

import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

public class QuesbkAudioLogVo  {

	// 主键Id
	  @BaseField
	  @GeneralField
	  @Column(isMajor = true, sortBy = true)
	private String id;

	// 专业ID
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private Integer categoryId;

	// 产品Id
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String productId;


	// 产品名称
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String productName;

	// 产品URL
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String productImageUrl;

	// 题目Id
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String quesId;

	// 题目详情
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String quesDetail;

	// 题目答案
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String quesAnswer;
	// 题目类型
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String quesType;

	// 题目答案音频url
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String quesAudioUrl;
		// 考点
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private List<QuesbkQues.KeyPoint> examPoint;

	// 用户Id
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	private String userId;
		// 答案提交时间
	  @GeneralField
	  @Column(canUpdate = true, sortBy = true)
	private String submitTime;	
		// 用户Id
	  @GeneralField
	  @Column(canUpdate = true, sortBy = false)
	 private String  traceId;
	  public QuesbkAudioLogVo(){};
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public String getQuesId() {
		return quesId;
	}
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	public String getQuesDetail() {
		return quesDetail;
	}
	public void setQuesDetail(String quesDetail) {
		this.quesDetail = quesDetail;
	}
	public String getQuesAnswer() {
		return quesAnswer;
	}
	public void setQuesAnswer(String quesAnswer) {
		this.quesAnswer = quesAnswer;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getQuesAudioUrl() {
		return quesAudioUrl;
	}
	public void setQuesAudioUrl(String quesAudioUrl) {
		this.quesAudioUrl = quesAudioUrl;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public List<QuesbkQues.KeyPoint> getExamPoint() {
		return examPoint;
	}
	public void setExamPoint(List<QuesbkQues.KeyPoint> examPoint) {
		this.examPoint = examPoint;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public class examPoint{
		String name;
		
	}
}
