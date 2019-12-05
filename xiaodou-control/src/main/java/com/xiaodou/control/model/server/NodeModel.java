package com.xiaodou.control.model.server;

import com.xiaodou.control.constant.Constant;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("node")
public class NodeModel extends MongoBaseModel {
	
	/**
	 * 主键ID
	 */
	@Pk
	private String nodeId;
	/**
	 *node节点mac地址
	 */
	private String mac;
	/**
	 * 
	 */
	private String groupId;
	
	private String state=Constant.MARK_INUSE;
	
/***************************拓展字段***************************/	
	/**
	 * 命令执行的版本
	 */
	private String version;
	/**
	 * 当前版本
	 */
	private String curVersion;	
	
	private String curWeight;
		
	
	/***************************继承字段***************************/	
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 机器别名
	 */
	private String alias;

	private String time;
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getCurWeight() {
		return curWeight;
	}

	public void setCurWeight(String curWeight) {
		this.curWeight = curWeight;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}




}
