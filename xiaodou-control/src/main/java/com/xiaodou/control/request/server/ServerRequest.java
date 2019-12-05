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
public class ServerRequest extends BaseRequest {
	/**
	 * 服务ID
	 */
	private String serverId;
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
	private String tomcatName;
	/**
	 * 服务节点
	 */
	private String serverNode;
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
	private String groupId;
	private String groupName;
	private String groupServiceType;

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

	public String getTomcatName() {
		return tomcatName;
	}

	public void setTomcatName(String tomcatName) {
		this.tomcatName = tomcatName;
	}

	public String getServerNode() {
		return serverNode;
	}

	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupServiceType() {
		return groupServiceType;
	}

	public void setGroupServiceType(String groupServiceType) {
		this.groupServiceType = groupServiceType;
	}

}
