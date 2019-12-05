package com.xiaodou.ms.vo;

import com.alibaba.fastjson.JSON;

public class PreCondListVO {
	private String preCond;
	private String threshold;
	public String getPreCond() {
		return preCond;
	}
	public void setPreCond(String preCond) {
		this.preCond = preCond;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
