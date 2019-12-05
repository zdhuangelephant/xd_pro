package com.xiaodou.ms.model.exam;

import com.xiaodou.autobuild.annotations.Column;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class QuestionBankExamRuleModel {

  // 主键Id
  private Integer id;

  // 名称
  private String name;

  // 产品Id
  private Long productId;

  // 试题类型
  private Integer examTypeId;

  @Column(persistent = false)
  private String examTypeName;

  // 规则细节
  private String ruleDetail;

  // 状态
  private Integer status;

  /** type 类型: 1 普通规则 0 默认规则 */
  private Integer type;

  // 章节号
  private Long chapterId;

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Long getProductId() {
	return productId;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public Integer getExamTypeId() {
	return examTypeId;
}

public void setExamTypeId(Integer examTypeId) {
	this.examTypeId = examTypeId;
}

public String getExamTypeName() {
	return examTypeName;
}

public void setExamTypeName(String examTypeName) {
	this.examTypeName = examTypeName;
}

public String getRuleDetail() {
	return ruleDetail;
}

public void setRuleDetail(String ruleDetail) {
	this.ruleDetail = ruleDetail;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public Integer getType() {
	return type;
}

public void setType(Integer type) {
	this.type = type;
}

public Long getChapterId() {
	return chapterId;
}

public void setChapterId(Long chapterId) {
	this.chapterId = chapterId;
}

  
}
