package com.xiaodou.ms.vo;

import java.util.List;

import lombok.Data;

@Data
public class ExamRuleVO {
  private List<InnerObj> itemList;
  @Data
  public static class InnerObj{
    private Integer cognitionLevel;
    private Integer easyLevel;
    private Integer itemType;
    private Long productChapterId;
    private Integer questionNum;
    private Integer questionType;
	public Integer getCognitionLevel() {
		return cognitionLevel;
	}
	public void setCognitionLevel(Integer cognitionLevel) {
		this.cognitionLevel = cognitionLevel;
	}
	public Integer getEasyLevel() {
		return easyLevel;
	}
	public void setEasyLevel(Integer easyLevel) {
		this.easyLevel = easyLevel;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Long getProductChapterId() {
		return productChapterId;
	}
	public void setProductChapterId(Long productChapterId) {
		this.productChapterId = productChapterId;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
    
  }
public List<InnerObj> getItemList() {
	return itemList;
}
public void setItemList(List<InnerObj> itemList) {
	this.itemList = itemList;
}
  
}
