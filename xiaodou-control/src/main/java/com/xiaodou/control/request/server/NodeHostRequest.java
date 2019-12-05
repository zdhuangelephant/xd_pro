package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;

public class NodeHostRequest extends BaseRequest {
	public NodeHostRequest(){}
	/**
	 * 节点HOST ID
	 */
	private String nodeId;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 机器别名
	 */
	private String alias;
	/**
	 * 命令集,存储NodeCommandPojo的泛型List
	 */
	private String nodeCommand;
	
	private String dockerStatus;
	
	private String dockerImages;
	
	private String containers;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNodeCommand() {
		return nodeCommand;
	}

	public void setNodeCommand(String nodeCommand) {
		this.nodeCommand = nodeCommand;
	}

	public String getDockerStatus() {
		return dockerStatus;
	}

	public void setDockerStatus(String dockerStatus) {
		this.dockerStatus = dockerStatus;
	}

	public String getDockerImages() {
		return dockerImages;
	}

	public void setDockerImages(String dockerImages) {
		this.dockerImages = dockerImages;
	}

	public String getContainers() {
		return containers;
	}

	public void setContainers(String containers) {
		this.containers = containers;
	}



}
