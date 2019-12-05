package com.xiaodou.control.request.server;

import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.request.BaseRequest;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class MiddleServerRequest extends BaseRequest {
	/**
	 * 服务ID
	 */
	private String serverId;
	/**
	 * 服务类型
	 */
	@NotEmpty
	private String serverType;
	/**
	 * 服务名称
	 */
	private String serverName;
	/**
	 * 安装命令
	 */
	private String installCommand;
	/**
	 * 用户名
	 */
	private String serverUserName;

	/**
	 * 密码
	 */
	private String serverPassword;
	/**
	 * 目录
	 */
	private String projectDir;

	/**
	 * 程序端口号
	 */
	private String projectPort;
	/**
	 * 服务SH命令执行用户
	 */
	private String user;
	
	private String groupId;
	
	private String timingMonitoring=Constant.close;
	
	/***************************rabbitMQ**************************/	
	/**
	 * 消息积压数量报警阈值
	 */
	private String blockMessageCount;
	
	/***************************Flume**************************/	
	/**
	 * flumeIp
	 */
	private String flumeIp;

	private String flumeRabbitMqIp;

	private String flumeRabbitMqQueueName;

	private String flumeRabbitMqUserName;

	private String flumeRabbitMqPassword;
	
	private String flumeRabbitMqPort;

	private String flumeRabbitMqExchange;
		

	public String getFlumeIp() {
		return flumeIp;
	}

	public void setFlumeIp(String flumeIp) {
		this.flumeIp = flumeIp;
	}

	public String getFlumeRabbitMqIp() {
		return flumeRabbitMqIp;
	}

	public void setFlumeRabbitMqIp(String flumeRabbitMqIp) {
		this.flumeRabbitMqIp = flumeRabbitMqIp;
	}

	public String getFlumeRabbitMqQueueName() {
		return flumeRabbitMqQueueName;
	}

	public void setFlumeRabbitMqQueueName(String flumeRabbitMqQueueName) {
		this.flumeRabbitMqQueueName = flumeRabbitMqQueueName;
	}

	public String getFlumeRabbitMqUserName() {
		return flumeRabbitMqUserName;
	}

	public void setFlumeRabbitMqUserName(String flumeRabbitMqUserName) {
		this.flumeRabbitMqUserName = flumeRabbitMqUserName;
	}

	public String getFlumeRabbitMqPassword() {
		return flumeRabbitMqPassword;
	}

	public void setFlumeRabbitMqPassword(String flumeRabbitMqPassword) {
		this.flumeRabbitMqPassword = flumeRabbitMqPassword;
	}

	public String getFlumeRabbitMqPort() {
		return flumeRabbitMqPort;
	}

	public void setFlumeRabbitMqPort(String flumeRabbitMqPort) {
		this.flumeRabbitMqPort = flumeRabbitMqPort;
	}

	public String getFlumeRabbitMqExchange() {
		return flumeRabbitMqExchange;
	}

	public void setFlumeRabbitMqExchange(String flumeRabbitMqExchange) {
		this.flumeRabbitMqExchange = flumeRabbitMqExchange;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getInstallCommand() {
		return installCommand;
	}

	public void setInstallCommand(String installCommand) {
		this.installCommand = installCommand;
	}

	public String getServerUserName() {
		return serverUserName;
	}

	public void setServerUserName(String serverUserName) {
		this.serverUserName = serverUserName;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getProjectDir() {
		return projectDir;
	}

	public void setProjectDir(String projectDir) {
		this.projectDir = projectDir;
	}

	public String getProjectPort() {
		return projectPort;
	}

	public void setProjectPort(String projectPort) {
		this.projectPort = projectPort;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBlockMessageCount() {
		return blockMessageCount;
	}

	public void setBlockMessageCount(String blockMessageCount) {
		this.blockMessageCount = blockMessageCount;
	}

	public String getTimingMonitoring() {
		return timingMonitoring;
	}

	public void setTimingMonitoring(String timingMonitoring) {
		this.timingMonitoring = timingMonitoring;
	}


		

}
