package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;

public class NodeMacRequest extends BaseRequest {
	public NodeMacRequest(){}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * mac地址
	 */
	private String mac;
	
}
