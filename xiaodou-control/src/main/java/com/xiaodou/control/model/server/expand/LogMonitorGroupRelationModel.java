package com.xiaodou.control.model.server.expand;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;
@CollectionName("LogMonitorGroupRelation")
public class LogMonitorGroupRelationModel extends MongoBaseModel {
	
	/**  主键ID */
	@Pk
	private String logMonitorGroupRelationId;
	/** logPath 日志收集Id */
	private String logMonitorId;
	/** 服务组Id */
	private String groupId;
	public String getLogMonitorGroupRelationId() {
		return logMonitorGroupRelationId;
	}
	public void setLogMonitorGroupRelationId(String logMonitorGroupRelationId) {
		this.logMonitorGroupRelationId = logMonitorGroupRelationId;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLogMonitorId() {
		return logMonitorId;
	}
	public void setLogMonitorId(String logMonitorId) {
		this.logMonitorId = logMonitorId;
	}

}
