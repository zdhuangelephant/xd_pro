package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;

public class QuesbkBase extends BaseEntity {
	/**
	 * 题库ID
	 */
	private Long id;

	/**
	 * 科目ID
	 */
	private Long categoryId;

	/**
	 * 题库名称
	 */
	private String name;

	/**
	 * 题库状态
	 */
	private String status;

	/**
	 * 是否付费
	 */
	private String expense;

	/**
	 * 问题数量
	 */
	private String quesCount;
	/**
	 * 课程信息
	 */
	private String courseInfoList;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCourseInfoList() {
		return courseInfoList;
	}

	public void setCourseInfoList(String courseInfoList) {
		this.courseInfoList = courseInfoList;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense == null ? null : expense.trim();
	}

	public String getQuesCount() {
		return quesCount;
	}

	public void setQuesCount(String quesCount) {
		this.quesCount = quesCount == null ? null : quesCount.trim();
	}

}
