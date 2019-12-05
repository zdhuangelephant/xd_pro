package com.xiaodou.ucerCenter.agent.model;

public class BlankNoticeModel {
	// 标识id
	private Long id;
	// 展示方式：0 每次都展示，1 只展示一次，2 不展示
	private Short type;
	// 展示地址 http://
	private String showUrl;
	// 跳转地址 http:// app://
	private String jumpUrl;
	//app的模块号，用来区分不同的app
	private String module;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}
