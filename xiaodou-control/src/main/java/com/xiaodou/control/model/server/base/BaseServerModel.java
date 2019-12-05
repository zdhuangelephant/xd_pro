package com.xiaodou.control.model.server.base;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("baseServer")
public class BaseServerModel extends MongoBaseModel {

	/**
	 * 基础服务ID
	 */
	@Pk
	private String baseServerId;
	/**
	 * 服务名称
	 */
	private String serverName;
	/**
	 * war包地址
	 */
	private String warAdress;
	/**
	 * tomcat端口号
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
	public String getBaseServerId() {
		return baseServerId;
	}
	public void setBaseServerId(String baseServerId) {
		this.baseServerId = baseServerId;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTomcatPort() {
		return tomcatPort;
	}
	public void setTomcatPort(String tomcatPort) {
		this.tomcatPort = tomcatPort;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
