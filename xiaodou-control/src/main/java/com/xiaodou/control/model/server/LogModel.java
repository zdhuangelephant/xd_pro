package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("log")
public class LogModel extends MongoBaseModel {
	/**
	 * 日志ID
	 */
	/** id 主键 */
	@Pk
	private String logId;
	/**
	 * mac地址
	 */
	private String mac;
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
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 日志信息
	 */
	private String msg;
	/**
	 * 部署的版本号
	 */
	private String version;

	private String userId;

	private String userName;
	
	private String alias;

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
