package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("nginx")
public class NginxRequest  extends BaseRequest{
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
	 * 别名
	 */
	private String upstreamName;
	
	/**
	 * 是否为IpHash策略
	 */
	private String ipHash="0";//是否为ipHash策略，0否1是
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新信息
	 */
	private String updateTime;
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getNginxListenPort() {
		return nginxListenPort;
	}
	public void setNginxListenPort(String nginxListenPort) {
		this.nginxListenPort = nginxListenPort;
	}
	public String getIpHash() {
		return ipHash;
	}
	public void setIpHash(String ipHash) {
		this.ipHash = ipHash;
	}


}
