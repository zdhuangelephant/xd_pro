package com.xiaodou.ms.model.exam;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class QuestionBankExamPaperModel {

  // 主键
  private String id;

  // 产品Id
  private Long productId;

  // 名称
  private String examName;

  // 题目数量
  private Integer quesNum;

  // 题目Id
  private String quesIds;

  // 描述
  private String mdesc;

  // 状态
  private String status;

  // 难度
  private String diffculty;

  // 试卷类型
  private Integer examTypeId;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Long getProductId() {
	return productId;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public String getExamName() {
	return examName;
}

public void setExamName(String examName) {
	this.examName = examName;
}

public Integer getQuesNum() {
	return quesNum;
}

public void setQuesNum(Integer quesNum) {
	this.quesNum = quesNum;
}

public String getQuesIds() {
	return quesIds;
}

public void setQuesIds(String quesIds) {
	this.quesIds = quesIds;
}

public String getMdesc() {
	return mdesc;
}

public void setMdesc(String mdesc) {
	this.mdesc = mdesc;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getDiffculty() {
	return diffculty;
}

public void setDiffculty(String diffculty) {
	this.diffculty = diffculty;
}

public Integer getExamTypeId() {
	return examTypeId;
}

public void setExamTypeId(Integer examTypeId) {
	this.examTypeId = examTypeId;
}

 
}
