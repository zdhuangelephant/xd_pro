package com.xiaodou.ms.enums;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;

public enum WrongQues {
	FIRST("1", "有错别字"),
	SECOND("2", "内容出错"),
	THIRD("3", "答案出错"),
	FOURTH("4", "其它");

	private String index;
	private String content;

	private WrongQues(String index, String content) {
		this.index = index;
		this.content = content;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex() {
		return index;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}

	private final static Map<String, WrongQues> _all = Maps.newHashMap();

	private static void init() {
		for (WrongQues degree : WrongQues.values()) {
			if (null == degree)
				continue;
			_all.put(degree.getIndex(), degree);
		}
	}

	static {
		init();
	}

	public static WrongQues getByCode(String index) {
		if (StringUtils.isNotBlank(index) && _all.containsKey(index))
			return _all.get(index);
		return FIRST;
	}
}