package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("nginxLog")
public class NginxLogModel extends MongoBaseModel {
	/**
	 * 日志ID
	 */
	/** id 主键 */
	@Pk
	private String logId;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 命令状态
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 日志信息
	 */
	private String msg;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	
}
