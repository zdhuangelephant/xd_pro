package com.xiaodou.autotest.web.request;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class JsFunctionRequest {
	private Long id;
	private String funSignature;
	private String functionBody;
	private Integer uid;
	private Timestamp createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunSignature() {
		return funSignature;
	}

	public void setFunSignature(String funSignature) {
		this.funSignature = funSignature;
	}

	public String getFunctionBody() {
		return functionBody;
	}

	public void setFunctionBody(String functionBody) {
		this.functionBody = functionBody;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
