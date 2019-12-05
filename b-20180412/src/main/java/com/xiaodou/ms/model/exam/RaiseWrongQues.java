package com.xiaodou.ms.model.exam;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RaiseWrongQues implements Serializable {
	private static final long serialVersionUID = 3379498552004854604L;
	/**
	 * id 主键
	 * 
	 */
	private Long id;
	/**
	 * 用户ID
	 * 
	 */
	private String userId;
	/**
	 * 课程ID
	 * 
	 */
	private Long courseId;
	/**
	 * // 章ID
	 * 
	 */
	private Long chapterId;
	/**
	 * 题目ID
	 * 
	 */
	private Long quesId;
	/**
	 * 错误类型
	 * 
	 */
	private String wrongType;
	/**
	 * 错误描述
	 * 
	 */
	private String wrongMsg;

	private String mdesc;
	private String errDesc;
	private Timestamp createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public String getWrongType() {
		return wrongType;
	}

	public void setWrongType(String wrongType) {
		this.wrongType = wrongType;
	}

	public String getWrongMsg() {
		return wrongMsg;
	}

	public void setWrongMsg(String wrongMsg) {
		this.wrongMsg = wrongMsg;
	}

	public String getMdesc() {
		return mdesc;
	}

	public void setMdesc(String mdesc) {
		this.mdesc = mdesc;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
