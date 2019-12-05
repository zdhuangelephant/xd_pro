package com.xiaodou.control.model.server;

import java.util.Date;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("nginxServerGroupRelation")
public class NginxServerGroupRelationModel extends MongoBaseModel {
	/**
	 * NginxId
	 */
	/** NginxId  */
	private String nginxServerId;
	/**
	 * serverGroupId
	 */
	private String serverGroupId;
	/**
	 * 创建时间
	 */
	private String createTime=new Date().toString();
	public String getNginxServerId() {
		return nginxServerId;
	}
	public void setNginxServerId(String nginxServerId) {
		this.nginxServerId = nginxServerId;
	}
	public String getServerGroupId() {
		return serverGroupId;
	}
	public void setServerGroupId(String serverGroupId) {
		this.serverGroupId = serverGroupId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	

}
