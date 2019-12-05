package com.xiaodou.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ExamSubmitStatus {

	SAVE("0"), SUBMIT("1");

	private String code;

	private static final Map<String, ExamSubmitStatus> codeMapping = Maps
			.newHashMap();

	static {
		for (ExamSubmitStatus status : ExamSubmitStatus.values()) {
			codeMapping.put(status.code, status);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	ExamSubmitStatus(String statusCode) {
		this.code = statusCode;
	}

	public static ExamSubmitStatus getByCode(String statusCode) {
		if (codeMapping.containsKey(statusCode))
			return codeMapping.get(statusCode);
		return null;
	}

}
