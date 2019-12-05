package com.xiaodou.control.vo;

import java.util.UUID;

import com.xiaodou.control.enums.StartupConfigCommand;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

public class ConfigInfoVo {
	public ConfigInfoVo() {
	}

	public ConfigInfoVo(StartupConfigCommand command) {
		this.id = UUID.randomUUID().toString();
		this.commandId = command.getCode();
		this.commandName = command.getName();
	}

	/** id 主键ID */
	private String id;
	/**
	 * 命令ID
	 */
	private String commandId;
	/**
	 * 命令名称
	 */
	private String commandName;
	/**
	 * 命令状态
	 */
	private String state;
	/** commandInfo 命令信息串 */
	private String commandInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(String commandInfo) {
		this.commandInfo = commandInfo;
	}

}
