package com.xiaodou.control.request.server;

import com.alibaba.fastjson.annotation.JSONField;

public class Ports {
	public Ports() {
	}

	@JSONField(name = "IP")
	private String ip;
	@JSONField(name = "Type")
	private String type;
	@JSONField(name = "PublicPort")
	private Integer publicPort;
	@JSONField(name = "PrivatePort")
	private Integer privatePort;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPublicPort() {
		return publicPort;
	}

	public void setPublicPort(Integer publicPort) {
		this.publicPort = publicPort;
	}

	public Integer getPrivatePort() {
		return privatePort;
	}

	public void setPrivatePort(Integer privatePort) {
		this.privatePort = privatePort;
	}

}
