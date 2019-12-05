package com.xiaodou.userCenter.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Latest {
	ISLATEST(1, "近期课程"), NOTLATEST(0, "其它非近期课程");
	private int code;
	private String name;

	private Latest(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final static Map<Integer, ExamType> _allExamTypeType = Maps
			.newHashMap();

	private static void init() {
		for (ExamType examType : ExamType.values()) {
			if (null == examType)
				continue;
			_allExamTypeType.put(examType.getCode(), examType);
		}
	}

	static {
		init();
	}

}
