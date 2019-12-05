package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("server")
public class ServerModel extends MongoBaseModel {
	/**
	 * 服务ID
	 */
	@Pk
	private String serverId;
	/**
	 * 基础服务ID
	 */
	private String baseServerId;

	
	private String groupId;
	
	/***************************拓展字段**************************/		
	/**
	 * FTP用户名
	 */
	private String userName;
	/**
	 * FTP密码
	 */
	private String passWord;
	/**
	 * FTPHOST
	 */
	private String host;
	/**
	 * FTP端口号
	 */
	private String port;
	
	/**
	 * 后置执行URL
	 */
	private String requestUrl;
	
	
	
/***************************继承字段***************************/	
	
	/**
	 * 服务名称
	 */
	private String serverName;
	/**
	 * war包地址
	 */
	private String warAdress;
	/**
	 * tomcat名称
	 */
	private String tomcatPort;


	/**
	 * 基础目录
	 */
	private String baseDir;
	/**
	 * 服务SH命令执行用户
	 */
	private String user;


	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getWarAdress() {
		return warAdress;
	}

	public void setWarAdress(String warAdress) {
		this.warAdress = warAdress;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public String getBaseServerId() {
		return baseServerId;
	}

	public void setBaseServerId(String baseServerId) {
		this.baseServerId = baseServerId;
	}

	public String getTomcatPort() {
		return tomcatPort;
	}

	public void setTomcatPort(String tomcatPort) {
		this.tomcatPort = tomcatPort;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
}
