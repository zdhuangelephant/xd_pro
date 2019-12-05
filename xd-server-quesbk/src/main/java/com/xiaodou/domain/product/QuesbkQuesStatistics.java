package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.util.QuesbkUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkQuesStatistics extends BaseEntity {

	public QuesbkQuesStatistics() {
		this.examTimes = 0;
		this.wrongTimes = 0;
		this.rightPercent = "0.00";
	}

	public void addTimes() {
		this.examTimes++;
		caculateRP();
	}

	public void addWrongTime() {
		this.wrongTimes++;
		addTimes();
	}

	private void caculateRP() {
		this.rightPercent = QuesbkUtil.getFormat().format(
				(new Double((examTimes - wrongTimes)) / new Double(examTimes)));
	}

	@Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private Long id;

	private Long courseId;

	private Long questionId;

	private Integer examTimes;

	private Integer wrongTimes;

	private String rightPercent;

	private String answerDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(Integer examTimes) {
		this.examTimes = examTimes;
	}

	public Integer getWrongTimes() {
		return wrongTimes;
	}

	public void setWrongTimes(Integer wrongTimes) {
		this.wrongTimes = wrongTimes;
	}

	public String getRightPercent() {
		return rightPercent;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent == null ? null : rightPercent.trim();
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail == null ? null : answerDetail.trim();
	}

	@Data
	public static class AnswerDetail {
		/**
		 * 问题ID
		 */
		private String id;
		/**
		 * 作答次数
		 */
		private Integer times;

		public void addTimes() {
			this.times++;
		}

		public Integer getTimes() {
			return times;
		}

		public void setTimes(Integer times) {
			this.times = times;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						QuesbkQuesStatistics.class,
						"xd_quesbk_ques_statistics",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
		;
	}
}
