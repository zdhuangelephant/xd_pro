package com.xiaodou.ms.vo.utils;

import lombok.Data;

@Data
public class CheckDataVO {
	private Integer charsCounts;
	private Integer bytesCounts;
	private String content;
	public Integer getCharsCounts() {
		return charsCounts;
	}
	public void setCharsCounts(Integer charsCounts) {
		this.charsCounts = charsCounts;
	}
	public Integer getBytesCounts() {
		return bytesCounts;
	}
	public void setBytesCounts(Integer bytesCounts) {
		this.bytesCounts = bytesCounts;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
