package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;

/**
 * 
 * @name NodeRequest CopyRright (c) 2016 by 51xiaodou.com
 * 
 * @author zhouhuan
 * @date 2016年8月1日
 * @description
 * @version 1.0
 */
public class NodeRequest extends BaseRequest {
	/**
	 * LogID 和 命令Id通用的UUID
	 */
	private String id;
	/**
	 * 命令状态
	 */
	private String state;
	/**
	 * mac
	 */
	private String mac;
	/**
	 * 节点Id
	 */
	private String nodeId;
	/**
	 * 服务ID
	 */
	private String serverId;
	/**
	 * 命令ID
	 */
	private String commandId;
	/**
	 * 命令名称
	 */
	private String commandName;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 日志
	 */
	private String msg;
	/**
	 * 命令详情
	 */
	private String commandInfo;

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

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(String commandInfo) {
		this.commandInfo = commandInfo;
	}
}
