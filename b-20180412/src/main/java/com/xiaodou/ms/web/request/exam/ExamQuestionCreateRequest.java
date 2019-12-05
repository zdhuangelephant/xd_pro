package com.xiaodou.ms.web.request.exam;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/7/11.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ExamQuestionCreateRequest extends BaseRequest {

  /**
   * 章节Id
   */
  private Long chapterId;

  /**
   * 课程Id
   */
  private Long courseId;

  /**
   * 题目类型
   */
  private Integer questionType;

  /**
   * 题目来源
   */
  private Integer resourceId;

  /**
   * 题目图片
   */
  private String quesImgUrl;

  /**
   * 认知程度
   */
  private Integer cognitionLevel;

  /**
   * 难易程度
   */
  private Integer diffcultLevel;

  /**
   * 题干
   */
  private String mdesc;

  /**
   * 解析
   */
  private String manalyze;

  /**
   * 单选题
   */
  private String radioSelection;

  /**
   * 多选题
   */
  private String checkBoxSelection;

  /**
   * 选项类型
   */
  private Integer optionType;

  /**
   * 是否真题
   */
  private Integer zhenti;

public Long getChapterId() {
	return chapterId;
}

public void setChapterId(Long chapterId) {
	this.chapterId = chapterId;
}

public Long getCourseId() {
	return courseId;
}

public void setCourseId(Long courseId) {
	this.courseId = courseId;
}

public Integer getQuestionType() {
	return questionType;
}

public void setQuestionType(Integer questionType) {
	this.questionType = questionType;
}

public Integer getResourceId() {
	return resourceId;
}

public void setResourceId(Integer resourceId) {
	this.resourceId = resourceId;
}

public String getQuesImgUrl() {
	return quesImgUrl;
}

public void setQuesImgUrl(String quesImgUrl) {
	this.quesImgUrl = quesImgUrl;
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

public String getMdesc() {
	return mdesc;
}

public void setMdesc(String mdesc) {
	this.mdesc = mdesc;
}

public String getManalyze() {
	return manalyze;
}

public void setManalyze(String manalyze) {
	this.manalyze = manalyze;
}

public String getRadioSelection() {
	return radioSelection;
}

public void setRadioSelection(String radioSelection) {
	this.radioSelection = radioSelection;
}

public String getCheckBoxSelection() {
	return checkBoxSelection;
}

public void setCheckBoxSelection(String checkBoxSelection) {
	this.checkBoxSelection = checkBoxSelection;
}

public Integer getOptionType() {
	return optionType;
}

public void setOptionType(Integer optionType) {
	this.optionType = optionType;
}

public Integer getZhenti() {
	return zhenti;
}

public void setZhenti(Integer zhenti) {
	this.zhenti = zhenti;
}

  

}
