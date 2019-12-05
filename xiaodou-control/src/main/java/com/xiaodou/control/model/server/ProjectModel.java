package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("project")
public class ProjectModel extends MongoBaseModel {
	/**
	 * ID
	 */
	/** id 主键 */
	@Pk
	private String projectId;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 程序名称
	 */
	private String projectName;

	/**
	 * 状态
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新信息
	 */
	private String updateTime;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
