package com.xiaodou.control.model.server.base;

import java.util.List;

import com.xiaodou.control.request.server.AllContainers;
import com.xiaodou.control.request.server.DockerImages;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("baseNode")
public class BaseNodeModel extends MongoBaseModel {
	/**
	 * mac地址
	 */
	@Pk
	private String mac;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 机器别名
	 */
	private String alias;

	private String time;
	
	/**
	 * 命令集,存储NodeCommandVo的泛型List
	 */
	private String nodeCommand;
	/** startupConfig 启动配置加载项 */
	private String startupConfig;
	
	private String dockerStatus;
	
	private String nginxConf;
	
	private List<DockerImages> dockerImages;
	
	private List<AllContainers> containers;
	
	private String status;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDockerStatus() {
		return dockerStatus;
	}

	public void setDockerStatus(String dockerStatus) {
		this.dockerStatus = dockerStatus;
	}

	public List<DockerImages> getDockerImages() {
		return dockerImages;
	}

	public void setDockerImages(List<DockerImages> dockerImages) {
		this.dockerImages = dockerImages;
	}

	public List<AllContainers> getContainers() {
		return containers;
	}

	public void setContainers(List<AllContainers> containers) {
		this.containers = containers;
	}

	public String getNodeCommand() {
		return nodeCommand;
	}

	public void setNodeCommand(String nodeCommand) {
		this.nodeCommand = nodeCommand;
	}

	public String getStartupConfig() {
		return startupConfig;
	}

	public void setStartupConfig(String startupConfig) {
		this.startupConfig = startupConfig;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNginxConf() {
		return nginxConf;
	}

	public void setNginxConf(String nginxConf) {
		this.nginxConf = nginxConf;
	}	

}
