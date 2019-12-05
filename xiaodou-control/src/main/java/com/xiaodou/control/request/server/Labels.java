package com.xiaodou.control.request.server;

import com.alibaba.fastjson.annotation.JSONField;

public class Labels {
	public Labels() {
	}

	private String vendor;
	private String name;
	private String license;
	@JSONField(name = "build-date")
	private String buildDate;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
}
