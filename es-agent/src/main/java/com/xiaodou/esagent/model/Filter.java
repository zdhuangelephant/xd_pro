package com.xiaodou.esagent.model;

import com.xiaodou.esagent.enums.CommonEnum.FilterType;

public class Filter {
	//操作类型
	private FilterType type;
	//数据值
	private String data;
	//字段名称
    private String column;
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}
}
