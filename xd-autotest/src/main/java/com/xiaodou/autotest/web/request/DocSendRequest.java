package com.xiaodou.autotest.web.request;

public class DocSendRequest {
	private String reqName;
	private String method;
	private String url;
	private String userId;
	private String params;
	private String preConds;
	private String preSets;
	private String testCases;
	private String afterSets;
	private String format;
	public String getReqName() {
		return reqName;
	}

	public void setReqName(String reqName) {
		this.reqName = reqName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getPreConds() {
		return preConds;
	}

	public void setPreConds(String preConds) {
		this.preConds = preConds;
	}

	public String getPreSets() {
		return preSets;
	}

	public void setPreSets(String preSets) {
		this.preSets = preSets;
	}

	public String getTestCases() {
		return testCases;
	}

	public void setTestCases(String testCases) {
		this.testCases = testCases;
	}

	public String getAfterSets() {
		return afterSets;
	}

	public void setAfterSets(String afterSets) {
		this.afterSets = afterSets;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
