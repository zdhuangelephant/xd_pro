package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by zyp on 15/8/11.
 */
@Data
public class ProductQuestionModel {

	// id
	private Long id;

	// 产品ID
	private Long productId;

	// 章节Id
	private Long chapterId;

	// 问题Id
	private Long questionId;

	// 创建事件
	private Timestamp createTime;

	// 认知层次
	private Integer cognitionLevel;

	// 难以程度
	private Integer diffcultLevel;

	// 试题类型
	private Integer questionType;

	// 题目主干
	private String questionDesc;

	// 章节
	private String chapterName;

	// 别名
	private String chapterIdAlias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCognitionLevel() {
		return cognitionLevel;
	}

	public void setCognitionLevel(Integer cognitionLevel) {
		this.cognitionLevel = cognitionLevel;
	}

	public Integer getDiffcultLevel() {
		return diffcultLevel;
	}

	public void setDiffcultLevel(Integer diffcultLevel) {
		this.diffcultLevel = diffcultLevel;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterIdAlias() {
		return chapterIdAlias;
	}

	public void setChapterIdAlias(String chapterIdAlias) {
		this.chapterIdAlias = chapterIdAlias;
	}

}
