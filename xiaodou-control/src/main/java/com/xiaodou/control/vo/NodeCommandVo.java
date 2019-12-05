package com.xiaodou.control.vo;

public class NodeCommandVo {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 命令ID
	 */
	private String commandId;
	/**
	 * 服务ID
	 */
	private String serverId;
	/**
	 * 命令名称
	 */
	private String commandName;
	/**
	 * 命令状态
	 */
	private String state;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 拓展字段，用于docker创建容器，其他命令可暂无值
	 */
	private String commandInfo;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(String commandInfo) {
		this.commandInfo = commandInfo;
	}

}
