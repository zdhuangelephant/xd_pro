package com.xiaodou.control.vo;

import com.xiaodou.control.model.server.NginxModel;
import com.xiaodou.summer.dao.mongo.annotion.Pk;

public class NginxConfVo {
	/**
	 * ID
	 */
	/** id 主键 */
	@Pk
	private String nginxServerId;
	/**
	 * 监控地址
	 */
	private String nginxServerName;
	/**
	 * 监控端口号
	 */
	private String nginxListenPort;

	/**
	 * 日志
	 */
	private String accessLog;
	
	/**
	 * 是否为IpHash策略
	 */
	private String ipHash="0";//是否为ipHash策略，0否1是
	
	/**
	 * 别名
	 */
	private String upstreamName;
	
	/**
	 * 分流节点
	 */
	private String upstreamNode;	
	/**
	 * 命令ID
	 */
	private String commandId;
	/**
	 * 命令名称
	 */
	private String commandName;
	public NginxConfVo(){};
	
	public NginxConfVo(NginxModel nginx){
		this.accessLog=nginx.getAccessLog();
		this.nginxListenPort=nginx.getNginxListenPort();
		this.nginxServerName=nginx.getNginxServerName();
		this.nginxServerId=nginx.getNginxServerId();
		this.upstreamName=nginx.getUpstreamName();
		this.ipHash=nginx.getIpHash();
	}
	public String getNginxServerId() {
		return nginxServerId;
	}
	public void setNginxServerId(String nginxServerId) {
		this.nginxServerId = nginxServerId;
	}
	public String getNginxServerName() {
		return nginxServerName;
	}
	public void setNginxServerName(String nginxServerName) {
		this.nginxServerName = nginxServerName;
	}

	public String getAccessLog() {
		return accessLog;
	}
	public void setAccessLog(String accessLog) {
		this.accessLog = accessLog;
	}
	public String getUpstreamName() {
		return upstreamName;
	}
	public void setUpstreamName(String upstreamName) {
		this.upstreamName = upstreamName;
	}
	public String getNginxListenPort() {
		return nginxListenPort;
	}
	public void setNginxListenPort(String nginxListenPort) {
		this.nginxListenPort = nginxListenPort;
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

	public String getUpstreamNode() {
		return upstreamNode;
	}

	public void setUpstreamNode(String  upstreamNode) {
		this.upstreamNode = upstreamNode;
	}

	public String getIpHash() {
		return ipHash;
	}

	public void setIpHash(String ipHash) {
		this.ipHash = ipHash;
	}

	public static  class UpstreamNode{
		private String  ipAdress;
		private String  strategy="";

		public String getIpAdress() {
			return ipAdress;
		}
		public void setIpAdress(String ipAdress) {
			this.ipAdress = ipAdress;
		}
		public String getStrategy() {
			return strategy;
		}
		public void setStrategy(String strategy) {
			this.strategy = strategy;
		}
	}
}
