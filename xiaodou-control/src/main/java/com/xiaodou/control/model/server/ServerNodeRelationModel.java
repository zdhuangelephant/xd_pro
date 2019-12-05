package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("serverNodeRelation")
public class ServerNodeRelationModel extends MongoBaseModel {
	/**
	 * 主键ID
	 */
	@Pk
	private String serverNodeId;
	/**
	 * 服务ID
	 */
	private String serverId;
	/**
	 * NodeId
	 */
	private String nodeId;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 服务当前部署的版本
	 */
	private String version;
	/**
	 * Nginx策略
	 */
	private String strategy="";

	private String weight="";
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerNodeId() {
		return serverNodeId;
	}

	public void setServerNodeId(String serverNodeId) {
		this.serverNodeId = serverNodeId;
	}
}
