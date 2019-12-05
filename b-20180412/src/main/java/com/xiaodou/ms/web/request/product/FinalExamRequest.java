package com.xiaodou.ms.web.request.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FinalExamRequest  extends BaseRequest {
	@Column(isMajor = true)
	private Long id;
	private String examName;
	private Integer questionNums;
	private Integer examSort;
	private Long courseId;
	public FinalExamModel initModel() {
		FinalExamModel model = new FinalExamModel();
		model.setId(id);
		model.setExamName(examName);
		model.setQuestionNums(questionNums);
		model.setSort(examSort);
		model.setCourseId(courseId);
		return model;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public Integer getQuestionNums() {
		return questionNums;
	}
	public void setQuestionNums(Integer questionNums) {
		this.questionNums = questionNums;
	}

	public Integer getExamSort() {
		return examSort;
	}
	public void setExamSort(Integer examSort) {
		this.examSort = examSort;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	
	
}
