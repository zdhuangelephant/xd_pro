package com.xiaodou.ms.model.exam;

import lombok.Data;

/**
 * Created by zyp on 15/7/27.
 */
@Data
public class ExamRuleItem {

	/**
	 * 章
	 */
	public static final Long product = 0L;
	/**
	 * 章
	 */
	public static final Long chapter = 1L;

	/**
	 * 节
	 */
	public static final Long childChapter = 2L;

	/**
	 * 知识点
	 */
	public static final Long keywords = 3L;

	/**
	 * 条目类型
	 */
	private Integer itemType;

	/**
	 * 章节Id
	 */
	private String chapterIds;

	/**
	 * 题目类型
	 */
	private Integer questionType;

	/**
	 * 题目数量
	 */
	private Long questionNum;

	/**
	 * 难易程度
	 */
	private Integer easyLevel;

	/**
	 * 认知层次
	 */
	private Integer cognitionLevel;

	/**
	 * 产品章节号
	 */
	private Long productChapterId;

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Long getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Long questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getEasyLevel() {
		return easyLevel;
	}

	public void setEasyLevel(Integer easyLevel) {
		this.easyLevel = easyLevel;
	}

	public Integer getCognitionLevel() {
		return cognitionLevel;
	}

	public void setCognitionLevel(Integer cognitionLevel) {
		this.cognitionLevel = cognitionLevel;
	}

	public Long getProductChapterId() {
		return productChapterId;
	}

	public void setProductChapterId(Long productChapterId) {
		this.productChapterId = productChapterId;
	}

	public static Long getProduct() {
		return product;
	}

	public static Long getChapter() {
		return chapter;
	}

	public static Long getChildchapter() {
		return childChapter;
	}

	public static Long getKeywords() {
		return keywords;
	}

}
