package com.xiaodou.control.model.server;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("ftpFile")
public class FtpFileModel extends MongoBaseModel {
	/**
	 * 
	 */
	/** id 主键 */
	@Pk
	private String fileId;
	/**
	 * 服务ID/命名空间ID
	 */
	private String serverId;
	/**
	 * 文件名称(唯一不变用来控制版本的标志)
	 */
	private String uniqueFileName;
	/**
	 * 真实文件名称
	 */
	private String fileName;

	/**
	 * 上传人
	 */
	private String userId;
	/**
	 * 上传人名称
	 */
	private String userName;
	/**
	 * 使用状态
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 日志信息
	 */
	private String msg;
	/**
	 * 版本号
	 */
	private String version;

	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getUniqueFileName() {
		return uniqueFileName;
	}
	public void setUniqueFileName(String uniqueFileName) {
		this.uniqueFileName = uniqueFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
