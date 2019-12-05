package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 
 * @name ServerRequest CopyRright (c) 2016 by 51xiaodou.com
 * 
 * @author zhouhuan
 * @date 2016年8月1日
 * @description
 * @version 1.0
 */
public class BaseServerRequest extends BaseRequest {
	/**
	 * 服务ID
	 */
	private String baseServerId;
	/**
	 * 服务名称
	 */
	@NotEmpty
	private String serverName;
	/**
	 * WAR包地址
	 */
	@NotEmpty
	private String warAdress;
	/**
	 * tomcat名称
	 */
	@NotEmpty
	private String tomcatPort;
	
	/**
	 * 基础目录
	 */
	@NotEmpty
	private String baseDir;
	/**
	 * sh执行的用户
	 */
	@NotEmpty
	private String user;


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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

}
