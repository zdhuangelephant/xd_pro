package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;

public class NodeNginxLogRequest extends BaseRequest {
	public NodeNginxLogRequest(){}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * mac地址
	 */
	private String mac;
	
	private String msg;
	private String state;
	
}
